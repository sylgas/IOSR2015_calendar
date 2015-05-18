package pl.edu.agh.student.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.model.Event;
import pl.edu.agh.student.db.model.User;
import pl.edu.agh.student.db.model.enums.EventAttendance;
import pl.edu.agh.student.db.repository.UserRepository;
import pl.edu.agh.student.dto.EventDto;

@Service
public class EventMapper extends AbstractMapper<Event, EventDto> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected EventDto toDtoIfNotNull(Event event) {
        Event.BaseData baseData = event.getBaseData();
        Event.AdditionalData additionalData = event.getAdditionalData();
        EventDto eventDto = new EventDto()
                .setId(event.getId())
                .setFacebookId(event.getFacebookId());

        if (baseData != null) {
            eventDto.setOwner(userMapper.toDto(baseData.getOwner()))
                    .setName(baseData.getName())
                    .setDescription(baseData.getDescription())
                    .setStartDate(baseData.getStartDate())
                    .setEndDate(baseData.getEndDate())
                    .setLocation(baseData.getLocation());
        }

        if (additionalData != null) {
            eventDto.setColor(additionalData.getColor())
            .setAttendance(additionalData.getAttendance());
        }
        return eventDto;
    }

    @Override
    protected Event fromDtoIfNotNull(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setBaseData(new Event.BaseData()
                .setOwner(userMapper.fromDto(eventDto.getOwner()))
                .setName(eventDto.getName())
                .setDescription(eventDto.getDescription())
                .setStartDate(eventDto.getStartDate())
                .setEndDate(eventDto.getEndDate())
                .setLocation(eventDto.getLocation()));
        event.setAdditionalData(new Event.AdditionalData()
                .setColor(eventDto.getColor())
                .setAttendance(eventDto.getAttendance()));
        return event;
    }

    public Event fromFacebookEvent(org.springframework.social.facebook.api.Event facebookEvent, EventAttendance attendance) {
        Event event = new Event();
        event.setFacebookId(facebookEvent.getId());

        String ownerId = facebookEvent.getOwner().getId();
        User owner = userRepository.findOne(ownerId);
        if (owner == null) {
            owner = new User().setFirstName(facebookEvent.getOwner().getName());
            owner.setId(ownerId);
            userRepository.save(owner);
        }

        Event.Location location = null;

        if (facebookEvent.isDateOnly())
            if (facebookEvent.getPlace() != null && facebookEvent.getPlace().getLocation() != null) {
                System.out.println("not null");
                location = new Event.Location()
                        .setLatitude(facebookEvent.getPlace().getLocation().getLatitude())
                        .setLongitude(facebookEvent.getPlace().getLocation().getLongitude());
            }

        event.setBaseData(new Event.BaseData()
                .setName(facebookEvent.getName())
                .setDescription(facebookEvent.getDescription())
                .setStartDate(facebookEvent.getStartTime())
                .setEndDate(facebookEvent.getEndTime())
                .setOwner(owner)
                .setLocation(location));
        event.setAdditionalData(new Event.AdditionalData()
                .setAttendance(attendance));

        return event;
    }
}
