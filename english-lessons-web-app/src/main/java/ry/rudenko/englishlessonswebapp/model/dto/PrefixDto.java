package ry.rudenko.englishlessonswebapp.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrefixDto {

  String prefix;

  public static PrefixDto makeDefault(String prefix) {
    return builder()
        .prefix(prefix)
        .build();
  }
}