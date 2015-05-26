package pl.edu.agh.student.db.repository;

import org.springframework.data.mongodb.repository.Query;
import pl.edu.agh.student.db.model.Event;

import java.util.List;

public interface EventRepository extends Repository<Event> {

    @Query(value = "{ 'baseData.invited.user.$id' : ?0 }")
    List<Event> findByInvited(String userId);

    @Query(value = "{ 'facebookId' : ?0 }")
    public List<Event> findByFacebookId(String facebookId);

    @Query(value = "{ 'baseData.attendance' : ?0 }")
    public List<Event> findByAttendance(String attendance);

    @Query(value = "{ 'baseData.invited.user.$id' : { $in : [?0]} } }")
    List<Event> findByInvited(String userId);
}
