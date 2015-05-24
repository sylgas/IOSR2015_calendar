package pl.edu.agh.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public EventDto save(EventDto event) {
        return mapper.toDto(eventRepository.save(mapper.fromDto(event)));
    }

    public List<EventDto> getAllByCurrentUser(HttpServletRequest request) {
        List<EventDto> list = getAllOwnedByCurrentUser(request);
        list.addAll(getAllThatInvitedCurrentUser(request));
        return list;
    }

    public List<EventDto> getAllOwnedByCurrentUser(HttpServletRequest request) {
        User user = userService.getUserByHttpServletRequest(request);
        return mapper.toDto(eventRepository.findByBaseDataOwner(user.getId()));
    }

    public List<EventDto> getAllThatInvitedCurrentUser(HttpServletRequest request) {
        User user = userService.getUserByHttpServletRequest(request);
        return mapper.toDto(eventRepository.findByInvited(user.getId()));
    }

    public void delete(String eventId) {
        eventRepository.delete(eventId);
    }
}
