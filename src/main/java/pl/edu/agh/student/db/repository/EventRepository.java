package pl.edu.agh.student.db.repository;

import org.springframework.data.mongodb.repository.Query;
import pl.edu.agh.student.db.model.Event;

import java.util.List;

public interface EventRepository extends Repository<Event> {
    @Query(value = "{ 'baseData.owner.$id' : ?0 }")
    List<Event> findByBaseDataOwner(String userId);

    @Query(value = "{ 'baseData.invited' : ?0 }")
    List<Event> findByInvited(String userId);
}
