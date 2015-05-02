package pl.edu.agh.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.student.db.model.Event;
import pl.edu.agh.student.service.EventService;

import java.util.List;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody Event event) {
        return eventService.save(event);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getAll() {
        return eventService.getAll();
    }

}
