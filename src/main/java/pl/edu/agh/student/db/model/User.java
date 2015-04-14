package pl.edu.agh.student.db.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User extends Identifiable {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
