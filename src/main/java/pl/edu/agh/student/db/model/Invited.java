package pl.edu.agh.student.db.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.social.facebook.api.RsvpStatus;

public class Invited {
    @DBRef
    private User user;
    private RsvpStatus responseStatus;

    public User getUser() {
        return user;
    }

    public Invited setUser(User user) {
        this.user = user;
        return this;
    }

    public RsvpStatus getResponseStatus() {
        return responseStatus;
    }

    public Invited setResponseStatus(RsvpStatus responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

}
