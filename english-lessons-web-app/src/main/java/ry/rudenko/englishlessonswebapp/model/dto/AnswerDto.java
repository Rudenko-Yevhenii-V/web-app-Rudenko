package ry.rudenko.englishlessonswebapp.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerDto {

  Long id;

  Integer order;

  String name;
}
