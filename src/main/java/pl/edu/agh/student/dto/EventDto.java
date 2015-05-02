package pl.edu.agh.student.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class EventDto {

    private String id;

    private String facebookId;

    private UserDto owner;

    @NotNull
    @Size(min = 1)
    private String name;

    private String description;

    @NotNull
    private Date startDate;

    private Date endDate;

    private String color;

    public String getId() {
        return id;
    }

    public EventDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public EventDto setFacebookId(String facebookId) {
        this.facebookId = facebookId;
        return this;
    }

    public UserDto getOwner() {
        return owner;
    }

    public EventDto setOwner(UserDto owner) {
        this.owner = owner;
        return this;
    }

    public String getName() {
        return name;
    }

    public EventDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public EventDto setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public EventDto setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getColor() {
        return color;
    }

    public EventDto setColor(String color) {
        this.color = color;
        return this;
    }
}
