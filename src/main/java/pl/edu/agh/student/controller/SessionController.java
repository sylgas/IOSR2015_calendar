package pl.edu.agh.student.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/session")
public class SessionController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String showSession ( HttpSession session) {
        return session.getId() + " " + session.isNew();
    }
}