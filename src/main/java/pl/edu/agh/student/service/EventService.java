package pl.edu.agh.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Invitation;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.model.User;
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

    private void saveNewEvents(PagedList<Invitation> facebookInvitations, EventAttendance attendance, Facebook facebook) {
        if (facebookInvitations != null) {
            System.out.println(facebookInvitations.size());
            facebookInvitations.forEach(facebookInvitation -> {
                if (eventRepository.findByFacebookId(facebookInvitation.getEventId()).isEmpty()) {
                    org.springframework.social.facebook.api.Event facebookEvent =
                            facebookService.getEvent(facebook, facebookInvitation.getEventId());
                    eventRepository.save(mapper.fromFacebookEvent(facebookEvent, attendance));
                }
            });
        }
    }

    public List<EventDto> getAllByCurrentUser(HttpServletRequest request) {
        User user = userService.getUserByHttpServletRequest(request);
        Facebook facebook = facebookService.getFacebookApiFromRequestSession(request);
        if (facebook != null) {
            saveNewEvents(facebookService.getAttendingEvents(facebook), EventAttendance.ATTENDING, facebook);
            saveNewEvents(facebookService.getDeclinedEvents(facebook), EventAttendance.DECLINED, facebook);
            saveNewEvents(facebookService.getMaybeAttendingEvents(facebook), EventAttendance.MAYBE, facebook);
            saveNewEvents(facebookService.getNoRepliesEvents(facebook), EventAttendance.NO_REPLIES, facebook);
        }
        return mapper.toDto(eventRepository.findByBaseDataOwner(user.getId()));
    }
}
