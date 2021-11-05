package ry.rudenko.englishlessonswebapp.model.dto;


import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ry.rudenko.englishlessonswebapp.enums.UserRole;
import ry.rudenko.englishlessonswebapp.model.entity.LessonEntity;
import ry.rudenko.englishlessonswebapp.model.entity.TodoEntity;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {



  @NonNull
  String firstName;

  String middleName;

  @NonNull
  String lastName;

  @NonNull
  String login;

  @NonNull
  String password;

  @NonNull
  Instant birthday;

  @NonNull
  UserRole role;


}




















