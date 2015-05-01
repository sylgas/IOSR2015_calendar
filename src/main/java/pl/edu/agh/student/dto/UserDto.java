package pl.edu.agh.student.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {

    private String id;
    private String facebookId;

    @NotNull
    @Size(min = 1)
    private String firstName;

    @NotNull
    @Size(min = 1)
    private String lastName;

    public String getId() {
        return id;
    }

    public UserDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public UserDto setFacebookId(String facebookId) {
        this.facebookId = facebookId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
