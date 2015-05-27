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
    public List<EventDto> getAllByCurrentUser(HttpServletRequest request) {
        return eventService.getAllByCurrentUser(request);
    }

    @RequestMapping(value = "{eventId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("eventId") String eventId) {
        eventService.delete(eventId);
    }

    @RequestMapping(value = "/synchronize", method = RequestMethod.GET)
    @ResponseBody
    public void synchronizeFacebookEvents(HttpServletRequest request) {
        eventService.synchronizeFacebookEvents(request, userService.getUserByHttpServletRequest(request));
    }

    @RequestMapping(value = "/{id}/{responseStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public void changeRsvpStatus(@PathVariable("id") String id,
                                 @PathVariable("responseStatus") String responseStatus, HttpServletRequest request) {
        eventService.changeResponseStatus(request, id, RsvpStatus.valueOf(responseStatus.toUpperCase()));
    }
}
