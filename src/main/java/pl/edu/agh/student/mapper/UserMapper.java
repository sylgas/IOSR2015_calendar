package pl.edu.agh.student.mapper;

import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.model.User;
import pl.edu.agh.student.dto.UserDto;

@Service
public class UserMapper extends AbstractMapper<User, UserDto> {

    @Override
    protected User fromDtoIfNotNull(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        return user.setFacebookId(userDto.getFacebookId())
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName());
    }

    @Override
    protected UserDto toDtoIfNotNull(User user) {
        return new UserDto()
                .setId(user.getId())
                .setFacebookId(user.getFacebookId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName());
    }
}
