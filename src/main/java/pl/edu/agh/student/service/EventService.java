package pl.edu.agh.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Invitation;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.RsvpStatus;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.model.Event;
import pl.edu.agh.student.db.model.Invited;
import pl.edu.agh.student.db.model.User;
import pl.edu.agh.student.db.repository.EventRepository;
import pl.edu.agh.student.dto.EventDto;
import pl.edu.agh.student.mapper.EventMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service("eventService")
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private FacebookService facebookService;

    public EventDto save(HttpServletRequest request, EventDto event) {
        Event eventDo = mapper.fromDto(event);
        User user = userService.getUserByHttpServletRequest(request);
        if (event.getId() == null) {
            Event.BaseData baseData = eventDo.getBaseData();
            List<Invited> invitedUsers = baseData.getInvited();
            invitedUsers.add(new Invited()
                    .setUser(user)
                    .setResponseStatus(RsvpStatus.ATTENDING));
            eventDo.setBaseData(baseData.setInvited(invitedUsers));
        }
        else if (event.getFacebookId() != null) {
            Event previousEvent = eventRepository.findOne(event.getId());
            RsvpStatus previousResponseStatus = getCurrentUserFromInvited(previousEvent.getBaseData()
                    .getInvited(), user.getId()).getResponseStatus();
            RsvpStatus currentResponseStatus = getCurrentUserFromInvited(eventDo.getBaseData()
                    .getInvited(), user.getId()).getResponseStatus();
            if (previousResponseStatus != currentResponseStatus) {
                changeFacebookRsvpStatus(request, eventDo.getFacebookId(), currentResponseStatus);
            }
        }
        return mapper.toDto(eventRepository.save(eventDo));
    }

    public Invited getCurrentUserFromInvited(List<Invited> invitedUsers, String currentUserId) {
        for (Invited invited: invitedUsers) {
            if (invited.getUser().getId().equals(currentUserId)) {
                return invited;
            }
        }
        return null;
    }

    public EventDto get(String id) {
        return mapper.toDto(eventRepository.findOne(id));
    }

    public List<EventDto> getAllByCurrentUser(HttpServletRequest request) {
        List<EventDto> list = getAllOwnedByCurrentUser(request);
        list.addAll(getAllThatInvitedCurrentUser(request));
        return new ArrayList<>(new HashSet<>(list));
    }

    public List<EventDto> getAllOwnedByCurrentUser(HttpServletRequest request) {
        User user = userService.getUserByHttpServletRequest(request);
        synchronizeFacebookEvents(request, user);
        return mapper.toDto(eventRepository.findByOwner(user.getId()));
    }

    public List<EventDto> getAllThatInvitedCurrentUser(HttpServletRequest request) {
        User user = userService.getUserByHttpServletRequest(request);
        synchronizeFacebookEvents(request, user);
        return mapper.toDto(eventRepository.findByInvited(user.getId()));
    }

    public void delete(String eventId) {
        eventRepository.delete(eventId);
    }

    public void synchronizeFacebookEvents(HttpServletRequest request, User user) {
        Facebook facebook = facebookService.getFacebookApiFromRequestSession(request);
        if (facebook != null) {
            updateEvents(facebookService.getAttendingEvents(facebook), user, RsvpStatus.ATTENDING, facebook);
            updateEvents(facebookService.getDeclinedEvents(facebook), user, RsvpStatus.DECLINED, facebook);
            updateEvents(facebookService.getMaybeAttendingEvents(facebook), user, RsvpStatus.MAYBE, facebook);
            updateEvents(facebookService.getNoRepliesEvents(facebook), user, RsvpStatus.NOT_REPLIED, facebook);
        }
    }

    private void updateEvents(PagedList<Invitation> facebookInvitations, User user, RsvpStatus rsvpStatus, Facebook facebook) {
        if (facebookInvitations != null) {
            facebookInvitations.forEach(facebookInvitation -> {
                org.springframework.social.facebook.api.Event facebookEvent =
                        facebookService.getEvent(facebook, facebookInvitation.getEventId());
                eventRepository.save(mapper.fromFacebookEvent(facebookEvent, user, rsvpStatus));
            });
        }
    }

    public void changeResponseStatus(HttpServletRequest request, String eventId, RsvpStatus rsvpStatus) {
        Event event = eventRepository.findOne(eventId);
        User user = userService.getUserByHttpServletRequest(request);

        String facebookId = event.getFacebookId();
        if (facebookId != null) {
            changeFacebookRsvpStatus(request, facebookId, rsvpStatus);
        }

        Event.BaseData baseData = event.getBaseData();
        List<Invited> invited = baseData.getInvited();
        invited.get(getInvitedIndexByUserId(invited, user.getId())).setResponseStatus(rsvpStatus);
        event.setBaseData(baseData.setInvited(invited));
        eventRepository.save(event);
    }

    public Integer getInvitedIndexByUserId(List<Invited> invitedUsers, String id) {
        int i = 0;
        for (Invited invited : invitedUsers) {
            if (invited.getUser().getId().equals(id)) return i;
            i++;
        }
        return null;
    }

    public void changeFacebookRsvpStatus(HttpServletRequest request, String facebookId, RsvpStatus rsvpStatus) {
        switch (rsvpStatus) {
            case ATTENDING:
                facebookService.acceptInvitation(facebookService.getFacebookApiFromRequestSession(request), facebookId);
                break;
            case DECLINED:
                facebookService.declineInvitation(facebookService.getFacebookApiFromRequestSession(request), facebookId);
                break;
            case MAYBE:
                facebookService.maybeInvitation(facebookService.getFacebookApiFromRequestSession(request), facebookId);
                break;
        }
    }
}
