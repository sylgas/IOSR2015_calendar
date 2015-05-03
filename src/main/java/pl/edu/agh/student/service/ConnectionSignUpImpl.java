package pl.edu.agh.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

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

        return userService.createUser(facebookId, profile.getFirstName(),
                profile.getLastName(), profile.getEmail());
    }
}
