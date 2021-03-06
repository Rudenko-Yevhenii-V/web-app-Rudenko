package ry.rudenko.englishlessonswebapp.model.dto;


import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

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
  LocalDate birthday;

}




















