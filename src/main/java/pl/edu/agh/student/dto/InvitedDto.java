package pl.edu.agh.student.dto;

import pl.edu.agh.student.db.model.ResponseStatus;

public class InvitedDto {
    private UserDto user;

    private ResponseStatus responseStatus = ResponseStatus.NONE;

    public UserDto getUser() {
        return user;
    }

    public InvitedDto setUser(UserDto user) {
        this.user = user;
        return this;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public InvitedDto setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }
}
