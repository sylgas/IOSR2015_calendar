package pl.edu.agh.student.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.model.Event;
import pl.edu.agh.student.dto.EventDto;

@Service
public class EventMapper extends AbstractMapper<Event, EventDto> {

    @Autowired
    private UserMapper userMapper;

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
            eventDto.setColor(additionalData.getColor());
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
                .setColor(eventDto.getColor()));
        return event;
    }


}
