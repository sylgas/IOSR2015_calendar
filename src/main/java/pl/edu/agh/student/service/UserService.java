package pl.edu.agh.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.model.User;
import pl.edu.agh.student.db.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String createUser(String facebookId, String firstName, String lastName) {
        User user = new User();
        user.setId(facebookId);
        user.setFacebookId(facebookId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return userRepository.save(user).getId();
    }

    public User getUserByUsername(String username) {
        return userRepository.findOne(username);
    }
}
