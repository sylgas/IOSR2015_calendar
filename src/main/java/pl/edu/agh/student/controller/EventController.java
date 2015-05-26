package pl.edu.agh.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.RsvpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.student.controller.exception.NotValidParamsException;
import pl.edu.agh.student.dto.EventDto;
import pl.edu.agh.student.service.EventService;
import pl.edu.agh.student.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public EventDto save(@RequestBody @Valid EventDto event, HttpServletRequest request, BindingResult bindingResult) throws NotValidParamsException {
        if (bindingResult.hasErrors()) {
            throw new NotValidParamsException();
        }
        return eventService.save(request, event);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<EventDto> getAllByInvited(HttpServletRequest request) {
        return eventService.getAllByInvited(request);
    }

    @RequestMapping(value = "/synchronize", method = RequestMethod.GET)
    @ResponseBody
    public void synchronizeFacebookEvents(HttpServletRequest request) {
        eventService.synchronizeFacebookEvents(request, userService.getUserByHttpServletRequest(request));
    }

    @RequestMapping(value = "/{id}/{rsvpStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public void changeRsvpStatus(@PathVariable("id") String id,
                                 @PathVariable("rsvpStatus") String rsvpStatus, HttpServletRequest request) {
        eventService.changeRsvpStatus(request, id, RsvpStatus.valueOf(rsvpStatus.toUpperCase()));
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public List<EventDto> getAll() {
        return eventService.getAll();
    }
}
