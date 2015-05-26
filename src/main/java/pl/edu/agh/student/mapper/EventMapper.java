package pl.edu.agh.student.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.RsvpStatus;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.model.Event;
import pl.edu.agh.student.db.model.Invited;
import pl.edu.agh.student.db.model.User;
import pl.edu.agh.student.db.repository.EventRepository;
import pl.edu.agh.student.db.repository.UserRepository;
import pl.edu.agh.student.dto.EventDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventMapper extends AbstractMapper<Event, EventDto> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private InvitedMapper invitedMapper;

    @Override
    protected EventDto toDtoIfNotNull(Event event) {
        Event.BaseData baseData = event.getBaseData();
        Event.AdditionalData additionalData = event.getAdditionalData();
        EventDto eventDto = new EventDto()
                .setId(event.getId())
                .setFacebookId(event.getFacebookId());

        if (baseData != null) {
            eventDto.setOwner(baseData.getOwner())
                    .setName(baseData.getName())
                    .setDescription(baseData.getDescription())
                    .setStartDate(baseData.getStartDate())
                    .setEndDate(baseData.getEndDate())
                    .setLocation(baseData.getLocation())
                    .setInvited(invitedMapper.toDto(baseData.getInvited()));
        }

        if (additionalData != null) {
            eventDto.setColor(additionalData.getColor());
        }
        return eventDto;
    }

    @Override
    protected Event fromDtoIfNotNull(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setFacebookId(eventDto.getFacebookId());
        event.setBaseData(new Event.BaseData()
                .setOwner(eventDto.getOwner())
                .setName(eventDto.getName())
                .setDescription(eventDto.getDescription())
                .setStartDate(eventDto.getStartDate())
                .setEndDate(eventDto.getEndDate())
                .setLocation(eventDto.getLocation())
                .setInvited(invitedMapper.fromDto(eventDto.getInvited())));
        event.setAdditionalData(new Event.AdditionalData()
                .setColor(eventDto.getColor()));
        return event;
    }

    public Event fromFacebookEvent(org.springframework.social.facebook.api.Event facebookEvent, User user, RsvpStatus rsvpStatus) {
        Event event = new Event();
        List<Event> databaseEvents = eventRepository.findByFacebookId(facebookEvent.getId());
        List<Invited> invitedUsers = new ArrayList<>();

        if (!databaseEvents.isEmpty()) {
            Event databaseEvent = databaseEvents.get(0);
            event.setId(databaseEvent.getId());
            event.setAdditionalData(databaseEvent.getAdditionalData());
            invitedUsers = databaseEvent.getBaseData().getInvited();
        }
        else {
            Invited invited = new Invited()
                    .setUser(user)
                    .setResponseStatus(rsvpStatus);
            invitedUsers.add(invited);
        }
        event.setFacebookId(facebookEvent.getId());

        Event.Location location = null;

        if (facebookEvent.isDateOnly())
            if (facebookEvent.getPlace() != null && facebookEvent.getPlace().getLocation() != null) {
                location = new Event.Location()
                        .setLatitude(facebookEvent.getPlace().getLocation().getLatitude())
                        .setLongitude(facebookEvent.getPlace().getLocation().getLongitude());
            }

        event.setBaseData(new Event.BaseData()
                .setName(facebookEvent.getName())
                .setDescription(facebookEvent.getDescription())
                .setStartDate(facebookEvent.getStartTime())
                .setEndDate(facebookEvent.getEndTime())
                .setOwner(facebookEvent.getOwner().getName())
                .setLocation(location)
                .setInvited(invitedUsers));

        return event;
    }
}
