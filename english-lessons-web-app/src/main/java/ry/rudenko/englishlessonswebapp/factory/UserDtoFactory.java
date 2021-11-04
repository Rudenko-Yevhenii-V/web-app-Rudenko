package ry.rudenko.englishlessonswebapp.factory;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import ry.rudenko.englishlessonswebapp.model.dto.UserDto;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;

@Component
public class UserDtoFactory {

  public UserDto createUserDto(UserEntity entity) {
    return UserDto.builder()
        .id(entity.getId())
        .firstName(entity.getFirstName())
        .middleName(entity.getMiddleName())
        .lastName(entity.getLastName())
        .login(entity.getLogin())
        .password(entity.getPassword())
        .birthday(entity.getBirthday())
        .role(entity.getRole())
        .lessons(entity.getLessonList())
        .todos(entity.getTodoList())
        .build();
  }

  public List<UserDto> createUserDtoList(List<UserEntity> entities) {
    return entities
        .stream()
        .map(this::createUserDto)
        .collect(Collectors.toList());
  }
}
