package pl.edu.agh.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.model.Event;
import pl.edu.agh.student.db.repository.EventRepository;
import pl.edu.agh.student.db.repository.UserRepository;

import java.util.List;

@Service("eventService")
public class EventService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public String save(Event event) {
        return eventRepository.save(event).getId();
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
