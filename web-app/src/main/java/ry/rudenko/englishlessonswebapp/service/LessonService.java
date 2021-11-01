package ry.rudenko.englishlessonswebapp.service;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.exception.NotFoundException;
import ry.rudenko.englishlessonswebapp.factory.LessonDtoFactory;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.LessonDto;
import ry.rudenko.englishlessonswebapp.model.entity.LessonEntity;
import ry.rudenko.englishlessonswebapp.model.entity.ThemeEntity;
import ry.rudenko.englishlessonswebapp.repository.LessonRepository;
import ry.rudenko.englishlessonswebapp.repository.ThemeRepository;


@Service
public class LessonService {
  final
  ThemeRepository themeRepository;
  final
  LessonRepository lessonRepository;
  final
  LessonDtoFactory lessonDtoFactory;

  public LessonService(ThemeRepository themeRepository, LessonRepository lessonRepository,
      LessonDtoFactory lessonDtoFactory) {
    this.themeRepository = themeRepository;
    this.lessonRepository = lessonRepository;
    this.lessonDtoFactory = lessonDtoFactory;
  }

  public List<LessonDto> createLessonDtoList(Long themeId, String prefix) {
    ThemeEntity theme = getThemeOrThrowNotFound(themeId);
    List<LessonEntity> lessons = theme
        .getLessonEntities()
        .stream()
        .filter(it -> it.getName().toLowerCase().startsWith(prefix.toLowerCase()))
        .collect(Collectors.toList());
    return lessonDtoFactory.createLessonDtoList(lessons);
  }

  public LessonDto createLessonDto(Long themeId, String lessonsName) {
    System.out.println(themeId);
    System.out.println(lessonsName);
    System.out.println("lessonRepository = " + lessonRepository);
    System.out.println("themeRepository = " + themeRepository);
    ThemeEntity theme = getThemeOrThrowNotFound(themeId);
    LessonEntity lesson = lessonRepository
        .saveAndFlush(LessonEntity.makeDefault(lessonsName.toUpperCase(), theme));
    return lessonDtoFactory.createLessonDto(lesson);
  }

  public AckDto deleteLesson(Long themeId, Long lessonId) {
    lessonRepository.deleteByIdAndThemeEntityId(lessonId, themeId);
    return AckDto.makeDefault(true);
  }

  private ThemeEntity getThemeOrThrowNotFound(Long themeId) {
    return themeRepository
        .findById(themeId)
        .orElseThrow(() ->
            new NotFoundException(String.format("Theme with id \"%s\" not found.", themeId))
        );
  }
}


