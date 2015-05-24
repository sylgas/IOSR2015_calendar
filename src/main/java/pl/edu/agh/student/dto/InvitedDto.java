package pl.edu.agh.student.dto;

import pl.edu.agh.student.db.model.ResponseStatus;

public class InvitedDto {
    private UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    private ResponseStatus responseStatus = ResponseStatus.NONE;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
