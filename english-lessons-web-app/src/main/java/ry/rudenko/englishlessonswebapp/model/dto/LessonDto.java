package ry.rudenko.englishlessonswebapp.model.dto;

import javax.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import ry.rudenko.englishlessonswebapp.model.entity.LessonEntity;
import ry.rudenko.englishlessonswebapp.model.entity.ThemeEntity;
import ry.rudenko.englishlessonswebapp.model.entity.TodoEntity;

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
  String name;

  @NonNull
  ThemeDto theme;

  public static LessonDto toDto(LessonEntity lessonEntity) {
    LessonDto model = new LessonDto();
    model.setName(lessonEntity.getName());
    model.setTheme(ThemeDto.builder()
            .id(lessonEntity.getThemeEntity().getId())
            .name(lessonEntity.getThemeEntity().getName())
        .build());
    return model;
  }

}
