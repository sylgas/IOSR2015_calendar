package pl.edu.agh.student.db.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User extends Identifiable {

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
