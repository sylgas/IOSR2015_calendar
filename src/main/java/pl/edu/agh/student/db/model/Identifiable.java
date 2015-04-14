package pl.edu.agh.student.db.model;

import org.springframework.data.annotation.Id;

public abstract class Identifiable {

    @Id
    private String id;

    public final String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
