package pl.edu.agh.student.dto;


import org.springframework.social.facebook.api.RsvpStatus;

public class InvitedDto {
    private UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    private RsvpStatus responseStatus = RsvpStatus.NOT_REPLIED;

    public RsvpStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(RsvpStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
