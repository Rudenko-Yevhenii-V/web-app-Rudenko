package ry.rudenko.englishlessonswebapp.model.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;
import ry.rudenko.englishlessonswebapp.enums.UserRole;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

  @NonNull
  String name;

  String middleName;

  @NonNull
  String lastName;

  @NonNull
  Instant birthday;

  @NonNull
  UserRole role;
}




















