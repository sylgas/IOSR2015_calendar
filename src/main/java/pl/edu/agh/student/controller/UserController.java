package pl.edu.agh.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.edu.agh.student.db.model.User;
import pl.edu.agh.student.db.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody String username) {
        User userModel = new User();
        userModel.setUsername(username);
        return userRepository.save(userModel).getId();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> getAll() {
        return userRepository.findAll();
    }

}
