package pl.edu.agh.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.student.db.repository.UserRepository;
import pl.edu.agh.student.dto.UserDto;
import pl.edu.agh.student.mapper.UserMapper;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    public UserDto save(UserDto user) {
        return userMapper.toDto(userRepository.save(userMapper.fromDto(user)));
    }

    public List<UserDto> findAll() {
        return userMapper.toDto(userRepository.findAll());
    }
}
