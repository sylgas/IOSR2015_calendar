package pl.edu.agh.student.db.repository;

import pl.edu.agh.student.db.model.User;

public interface UserRepository extends Repository<User> {

    User findByUsername(String username);
}
