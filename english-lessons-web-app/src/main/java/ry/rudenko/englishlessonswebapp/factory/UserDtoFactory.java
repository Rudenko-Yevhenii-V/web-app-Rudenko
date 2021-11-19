package ry.rudenko.englishlessonswebapp.factory;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import ry.rudenko.englishlessonswebapp.model.dto.UserDto;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;

@Component
public class UserDtoFactory {

  public UserDto createUserDto(UserEntity entity) {
    return UserDto.builder()
        .name(entity.getName())
        .middleName(entity.getMiddleName())
        .lastName(entity.getLastName())
        .birthday(LocalDate.from(LocalDateTime.ofInstant(entity.getBirthday(), ZoneOffset.UTC)))
        .build();
  }

  public List<UserDto> createUserDtoList(List<UserEntity> entities) {
    return entities
        .stream()
        .map(this::createUserDto)
        .collect(Collectors.toList());
  }
}
