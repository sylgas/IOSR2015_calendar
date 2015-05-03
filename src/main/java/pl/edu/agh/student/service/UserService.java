package pl.edu.agh.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.model.User;
import pl.edu.agh.student.db.repository.UserRepository;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String createUser(String facebookId, String firstName, String lastName, String email) {
        User user = new User();
        user.setId(facebookId);
        user.setFacebookId(facebookId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return userRepository.save(user).getId();
    }

    public String saveUser(User user) {
        return userRepository.save(user).getId();
    }

    public User getUserByFacebookId(String id) {
        return userRepository.findOne(id);
    }

    public User getUserByHttpServletRequest(HttpServletRequest request) {
        SocialUser socialUser = (SocialUser) ((SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getPrincipal();
        return getUserByFacebookId(socialUser.getUserId());
    }
}
