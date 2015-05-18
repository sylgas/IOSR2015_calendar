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
import java.util.ArrayList;
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

    private List<EventDto> saveNewEvents(PagedList<Invitation> facebookInvitations, EventAttendance attendance, String color, Facebook facebook) {
        List<EventDto> events = new ArrayList<>();
        if (facebookInvitations != null) {
            facebookInvitations.forEach(facebookInvitation -> {
                if (eventRepository.findByFacebookId(facebookInvitation.getEventId()).isEmpty()) {
                    org.springframework.social.facebook.api.Event facebookEvent =
                            facebookService.getEvent(facebook, facebookInvitation.getEventId());
                    events.add(mapper.toDto(eventRepository.save(mapper.fromFacebookEvent(facebookEvent, attendance, color))));
                }
            });
        }
        return events;
    }

    public List<EventDto> getAllByCurrentUser(HttpServletRequest request) {
        User user = userService.getUserByHttpServletRequest(request);
        return mapper.toDto(eventRepository.findByBaseDataOwner(user.getId()));
    }

    public List<EventDto> getNotSynchronized(HttpServletRequest request) {
        Facebook facebook = facebookService.getFacebookApiFromRequestSession(request);
        List<EventDto> events = new ArrayList<>();
        if (facebook != null) {
            events.addAll(saveNewEvents(facebookService.getAttendingEvents(facebook), EventAttendance.ATTENDING, "#5CAD5C", facebook));
            events.addAll(saveNewEvents(facebookService.getDeclinedEvents(facebook), EventAttendance.DECLINED, "#EBECEC", facebook));
            events.addAll(saveNewEvents(facebookService.getMaybeAttendingEvents(facebook), EventAttendance.MAYBE, "#C2C2C2", facebook));
            events.addAll(saveNewEvents(facebookService.getNoRepliesEvents(facebook), EventAttendance.NO_REPLIES, "#C6AA8D", facebook));
        }
        return events;
    }

    public void changeAttendance(HttpServletRequest request, String eventId, String attendance) {
        Event event = eventRepository.findByFacebookId(eventId).get(0);
        EventAttendance newAttendance = EventAttendance.valueOf(attendance.toUpperCase());
        switch (newAttendance) {
            case ATTENDING:
                event.setAdditionalData(event.getAdditionalData().setAttendance(newAttendance).setColor("#5CAD5C"));
                facebookService.acceptInvitation(facebookService.getFacebookApiFromRequestSession(request), eventId);
            case DECLINED:
                event.setAdditionalData(event.getAdditionalData().setAttendance(newAttendance).setColor("#EBECEC"));
                facebookService.declineInvitation(facebookService.getFacebookApiFromRequestSession(request), eventId);
            case MAYBE:
                event.setAdditionalData(event.getAdditionalData().setAttendance(newAttendance).setColor("#C2C2C2"));
                facebookService.maybeInvitation(facebookService.getFacebookApiFromRequestSession(request), eventId);
        }
        eventRepository.save(event);
    }
}
