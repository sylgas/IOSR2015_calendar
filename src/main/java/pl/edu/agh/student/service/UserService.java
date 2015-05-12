package pl.edu.agh.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.repository.UserRepository;
import pl.edu.agh.student.dto.UserDto;
import pl.edu.agh.student.mapper.UserMapper;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    public UserDto save(UserDto user) {
        return userMapper.toDto(userRepository.save(userMapper.fromDto(user)));
    }

    public List<UserDto> findAll() {
        return userMapper.toDto(userRepository.findAll());
    }
}

//    public String createUser(String facebookId, String firstName, String lastName, String email) {
//        User user = new User();
//        user.setId(facebookId);
//        user.setFacebookId(facebookId);
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setEmail(email);
//        return userRepository.save(user).getId();
//    }
//
//    public String saveUser(User user) {
//        return userRepository.save(user).getId();
//    }
//
//    public User getUserByFacebookId(String id) {
//        return userRepository.findOne(id);
//    }
//
//    public User getUserByHttpServletRequest(HttpServletRequest request) {
//        if(request.getSession().getAttribute("SPRING_SECURITY_CONTEXT") != null) {
//            SocialUser socialUser = (SocialUser) ((SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getPrincipal();
//            return getUserByFacebookId(socialUser.getUserId());
//        }
//        return null;
//    }