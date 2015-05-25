package pl.edu.agh.student.db.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Invited {
    @DBRef
    private User user;
    private ResponseStatus responseStatus;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

}
