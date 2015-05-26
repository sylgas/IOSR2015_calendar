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
        if (event.getId() == null) {
            User user = userService.getUserByHttpServletRequest(request);
            Event.BaseData baseData = eventDo.getBaseData();
            List<Invited> invitedUsers = baseData.getInvited();
            invitedUsers.add(new Invited()
                    .setUser(user)
                    .setResponseStatus(RsvpStatus.ATTENDING));
            eventDo.setBaseData(baseData.setInvited(invitedUsers));
        }
        return mapper.toDto(eventRepository.save(eventDo));
    }

    public EventDto get(String id) {
        return mapper.toDto(eventRepository.findOne(id));
    }

    public List<EventDto> getAllByInvited(HttpServletRequest request) {
        User user = userService.getUserByHttpServletRequest(request);
        synchronizeFacebookEvents(request, user);
        return mapper.toDto(eventRepository.findByInvited(user.getId()));
    }

    public List<EventDto> getAll() {
        return mapper.toDto(eventRepository.findAll());
    }
    
    public List<EventDto> getAllByCurrentUser(HttpServletRequest request) {
        User user = userService.getUserByHttpServletRequest(request);
        synchronizeFacebookEvents(request, user);
        return mapper.toDto(eventRepository.findByInvited(user.getId()));
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

    public void changeRsvpStatus(HttpServletRequest request, String eventId, RsvpStatus rsvpStatus) {
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
