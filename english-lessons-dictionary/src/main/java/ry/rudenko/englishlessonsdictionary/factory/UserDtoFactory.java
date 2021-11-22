package ry.rudenko.englishlessonsdictionary.factory;

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
}
