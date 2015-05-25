package pl.edu.agh.student.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.model.Invited;
import pl.edu.agh.student.dto.InvitedDto;

@Service
public class InvitedMapper extends AbstractMapper<Invited, InvitedDto> {

    @Autowired
    private UserMapper userMapper;

    @Override
    protected Invited fromDtoIfNotNull(InvitedDto invitedDto) {
        Invited invited = new Invited();
        invited.setResponseStatus(invitedDto.getResponseStatus());
        invited.setUser(userMapper.fromDto(invitedDto.getUser()));
        return invited;
    }

    @Override
    protected InvitedDto toDtoIfNotNull(Invited invited) {
        InvitedDto invitedDto = new InvitedDto();
        invitedDto.setResponseStatus(invited.getResponseStatus());
        invitedDto.setUser(userMapper.toDto(invited.getUser()));
        return invitedDto;
    }
}
