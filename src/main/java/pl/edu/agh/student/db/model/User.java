package pl.edu.agh.student.db.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User extends Identifiable {

    private String facebookId;
    private String firstName;
    private String lastName;

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
