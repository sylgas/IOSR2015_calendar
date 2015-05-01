package pl.edu.agh.student.db.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
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

    public class Invited {
        @DBRef
        private User user;
        private String rspvStatus;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getRspvStatus() {
            return rspvStatus;
        }

        public void setRspvStatus(String rspvStatus) {
            this.rspvStatus = rspvStatus;
        }
    }

    public static class BaseData {
        @DBRef
        private User owner;
        private String name;
        private String description;
        private Date startDate;
        private Date endDate;
        private List<Invited> invited;

        public User getOwner() {
            return owner;
        }

        public BaseData setOwner(User owner) {
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
        private int color;

        public int getColor() {
            return color;
        }

        public AdditionalData setColor(int color) {
            this.color = color;
            return this;
        }
    }
}
