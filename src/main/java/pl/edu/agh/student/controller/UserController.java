package pl.edu.agh.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.student.controller.exception.NotValidParamsException;
import pl.edu.agh.student.dto.UserDto;
import pl.edu.agh.student.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public UserDto create(@RequestBody @Valid UserDto user, BindingResult bindingResult) throws NotValidParamsException {
        if (bindingResult.hasErrors()) {
            throw new NotValidParamsException();
        }
        return userService.save(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<UserDto> getAll() {
        return userService.findAll();
    }

}
