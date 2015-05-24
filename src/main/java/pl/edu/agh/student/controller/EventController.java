package pl.edu.agh.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.student.controller.exception.NotValidParamsException;
import pl.edu.agh.student.dto.EventDto;
import pl.edu.agh.student.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public EventDto save(@RequestBody @Valid EventDto event, BindingResult bindingResult) throws NotValidParamsException {
        if (bindingResult.hasErrors()) {
            throw new NotValidParamsException();
        }
        return eventService.save(event);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<EventDto> getAllByCurrentUser(HttpServletRequest request) {
        return eventService.getAllByCurrentUser(request);
    }

    @RequestMapping(value = "{eventId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("eventId") String eventId) {
        eventService.delete(eventId);
    }
}
