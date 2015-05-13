package pl.edu.agh.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.dto.UserDto;

@Service
public class ConnectionSignUpImpl implements ConnectionSignUp {

    @Autowired
    private UserService userService;

    @Override
    public String execute(Connection<?> connection) {
        //get universal data
        UserProfile profile = connection.fetchUserProfile();

        //get faceboook specific data
        Connection<Facebook> facebookConnection = (Connection<Facebook>) connection;
        String facebookId = facebookConnection.createData().getProviderUserId();

        UserDto userDto = new UserDto()
                .setId(facebookId)
                .setFirstName(profile.getFirstName())
                .setLastName(profile.getLastName());

        return userService.save(userDto).getId();
    }
}
