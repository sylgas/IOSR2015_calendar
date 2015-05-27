package pl.edu.agh.student.db.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.social.facebook.api.RsvpStatus;
import pl.edu.agh.student.db.model.Event;

import java.util.List;

public interface EventRepository extends Repository<Event> {

    @Query(value = "{ 'baseData.invited.user.$id' : ?0 }")
    List<Event> findByInvited(String userId);

    @Query(value = "{ $and: [{'baseData.invited.user.$id' : ?0},{'facebookId':{$ne:null}}] }")
    List<Event> findFacebookByInvited(String userId);

    @Query(value = "{ $and: [ {'baseData.invited.user.$id' : ?0 }, {'baseData.invited.responseStatus' : ?1 }]}")
    List<Event> findByInvitedAndRsvpStatus(String userId, RsvpStatus status);

    @Query(value = "{ 'facebookId' : ?0 }")
    public List<Event> findByFacebookId(String facebookId);

    @Query(value = "{ 'baseData.owner' : ?0 }")
    List<Event> findByOwner(String userId);

    @Query(value = "{ $and: [ {'baseData.owner' : ?0 }, {'facebookId':{$ne:null}}]}")
    List<Event> findFacebookByOwner(String userId);
}
