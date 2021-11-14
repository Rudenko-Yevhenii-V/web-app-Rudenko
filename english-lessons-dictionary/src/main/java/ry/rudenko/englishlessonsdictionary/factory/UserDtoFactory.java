package ry.rudenko.englishlessonsdictionary.factory;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ry.rudenko.englishlessonsdictionary.entity.UserEntity;
import ry.rudenko.englishlessonsdictionary.entity.dto.UserDto;


@Component
public class UserDtoFactory {

  public UserDto createUserDto(UserEntity entity) {
    return UserDto.builder()
        .firstName(entity.getName())
        .email(entity.getEmail())
        .build();
  }

  public List<UserDto> createUserDtoList(List<UserEntity> entities) {
    return entities
        .stream()
        .map(this::createUserDto)
        .collect(Collectors.toList());
  }
}
