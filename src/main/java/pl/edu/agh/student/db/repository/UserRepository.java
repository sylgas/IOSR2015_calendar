package pl.edu.agh.student.db.repository;

import org.springframework.data.mongodb.repository.Query;
import pl.edu.agh.student.db.model.User;

import java.util.List;

public interface UserRepository extends Repository<User> {
    @Query("{ $or: [ {'firstName': { $regex: ?0, $options: 'i' } }, {'lastName': { $regex: ?0, $options: 'i' } } ] }")
    List<User> findByNameOrLastNameRegex(String regex);
}
