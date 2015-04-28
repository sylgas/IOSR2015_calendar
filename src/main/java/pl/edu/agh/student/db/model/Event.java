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

    public class BaseData {
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

        public void setOwner(User owner) {
            this.owner = owner;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public List<Invited> getInvited() {
            return invited;
        }

        public void setInvited(List<Invited> invited) {
            this.invited = invited;
        }
    }

    public class AdditionalData {
        @DBRef
        private User user;
        private String comments;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }
    }
}
