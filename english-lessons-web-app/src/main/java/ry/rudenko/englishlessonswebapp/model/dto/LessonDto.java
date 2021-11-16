package ry.rudenko.englishlessonswebapp.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ry.rudenko.englishlessonswebapp.model.entity.LessonEntity;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonDto {

  @NonNull
  Long id;

  @NonNull
  @Column(length = 10485760)
  String text;

  @NonNull
  ThemeDto theme;

  public static LessonDto toDto(LessonEntity lessonEntity) {
    LessonDto model = new LessonDto();
    model.setText(lessonEntity.getText());
    model.setTheme(ThemeDto.builder()
            .id(lessonEntity.getThemeEntity().getId())
            .name(lessonEntity.getThemeEntity().getName())
        .build());
    return model;
  }
}
