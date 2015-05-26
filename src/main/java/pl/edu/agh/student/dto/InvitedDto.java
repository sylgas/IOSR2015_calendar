package pl.edu.agh.student.dto;


import org.springframework.social.facebook.api.RsvpStatus;

public class InvitedDto {
    private UserDto user;
    
    private RsvpStatus responseStatus = RsvpStatus.NOT_REPLIED;

    public UserDto getUser() {
        return user;
    }

    public InvitedDto setUser(UserDto user) {
        this.user = user;
        return this;
    }

    public RsvpStatus getResponseStatus() {
        return responseStatus;
    }

    public InvitedDto setResponseStatus(RsvpStatus responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }
}
