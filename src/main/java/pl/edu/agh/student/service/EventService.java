package pl.edu.agh.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Invitation;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.model.User;
import pl.edu.agh.student.db.model.Event;
import pl.edu.agh.student.db.model.enums.EventAttendance;
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

    public EventDto save(EventDto event) {
        return mapper.toDto(eventRepository.save(mapper.fromDto(event)));
    }

    public List<EventDto> getAll() {
        return mapper.toDto(eventRepository.findAll());
    }

    public List<EventDto> getByAttendance(String attendance) {
        return mapper.toDto(eventRepository.findByAttendance(attendance));
    }

    public void synchronizeFacebookEvents(HttpServletRequest request) {
        Facebook facebook = facebookService.getFacebookApiFromRequestSession(request);
        if (facebook != null) {
            updateEvents(facebookService.getAttendingEvents(facebook), EventAttendance.ATTENDING, facebook);
            updateEvents(facebookService.getDeclinedEvents(facebook), EventAttendance.DECLINED, facebook);
            updateEvents(facebookService.getMaybeAttendingEvents(facebook), EventAttendance.MAYBE, facebook);
            updateEvents(facebookService.getNoRepliesEvents(facebook), EventAttendance.NO_REPLIES, facebook);
        }
    }

    private void updateEvents(PagedList<Invitation> facebookInvitations, EventAttendance attendance, Facebook facebook) {
        if (facebookInvitations != null) {
            facebookInvitations.forEach(facebookInvitation -> {
                org.springframework.social.facebook.api.Event facebookEvent =
                        facebookService.getEvent(facebook, facebookInvitation.getEventId());
                eventRepository.save(mapper.fromFacebookEvent(facebookEvent, attendance));
            });
        }
    }

    public List<EventDto> getAllByCurrentUser(HttpServletRequest request) {
        User user = userService.getUserByHttpServletRequest(request);
        return mapper.toDto(eventRepository.findByBaseDataOwner(user.getId()));
    }

    public void changeAttendance(HttpServletRequest request, String eventId, String attendance) {
        Event event = eventRepository.findOne(eventId);
        EventAttendance newAttendance = EventAttendance.valueOf(attendance.toUpperCase());

        String facebookId = event.getFacebookId();
        if (facebookId != null) {
            changeFacebookAttendance(request, facebookId, newAttendance);
        }

        event.setBaseData(event.getBaseData().setAttendance(newAttendance));
        eventRepository.save(event);
    }

    public void changeFacebookAttendance(HttpServletRequest request, String facebookId, EventAttendance attendance) {
        switch (attendance) {
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
