package pl.edu.agh.student.db.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "events")
public class Event extends Identifiable {

    private String facebookId;
    private BaseData baseData;
    private AdditionalData additionalData;

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public BaseData getBaseData() {
        return baseData;
    }

    public void setBaseData(BaseData baseData) {
        this.baseData = baseData;
    }

    public AdditionalData getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(AdditionalData additionalData) {
        this.additionalData = additionalData;
    }

    public static class BaseData {
        private String owner;
        private String name;
        private String description;
        private Date startDate;
        private Date endDate;
        private List<Invited> invited;
        private Location location;

        public String getOwner() {
            return owner;
        }

        public BaseData setOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public String getName() {
            return name;
        }

        public BaseData setName(String name) {
            this.name = name;
            return this;
        }

        public Location getLocation() {
            return location;
        }

        public BaseData setLocation(Location location) {
            this.location = location;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public BaseData setDescription(String description) {
            this.description = description;
            return this;
        }

        public Date getStartDate() {
            return startDate;
        }

        public BaseData setStartDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Date getEndDate() {
            return endDate;
        }

        public BaseData setEndDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public List<Invited> getInvited() {
            return invited;
        }

        public BaseData setInvited(List<Invited> invited) {
            this.invited = invited;
            return this;
        }
    }

    public static class AdditionalData {
        private String color;

        public String getColor() {
            return color;
        }

        public AdditionalData setColor(String color) {
            this.color = color;
            return this;
        }
    }

    public static class Location {

        private double longitude;

        private double latitude;

        public double getLongitude() {
            return longitude;
        }

        public Location setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public double getLatitude() {
            return latitude;
        }

        public Location setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }
    }

}
