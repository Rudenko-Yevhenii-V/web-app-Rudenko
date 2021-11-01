package ry.rudenko.englishlessonswebapp.factory;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ry.rudenko.englishlessonswebapp.model.dto.LessonDto;
import ry.rudenko.englishlessonswebapp.model.entity.LessonEntity;

@RequiredArgsConstructor
  @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
  @Component
  public class LessonDtoFactory {

    ThemeDtoFactory themeDtoFactory;

    public LessonDto createLessonDto(LessonEntity entity) {
      return LessonDto.builder()
          .id(entity.getId())
          .name(entity.getName())
          .theme(themeDtoFactory.createThemeDto(entity.getThemeEntity()))
          .build();
    }

    public List<LessonDto> createLessonDtoList(List<LessonEntity> entities) {
      return entities
          .stream()
          .map(this::createLessonDto)
          .collect(Collectors.toList());
    }
  }
