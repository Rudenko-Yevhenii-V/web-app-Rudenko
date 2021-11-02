package ry.rudenko.englishlessonswebapp.service;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.exception.NotFoundException;
import ry.rudenko.englishlessonswebapp.factory.LessonDtoFactory;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.LessonDto;
import ry.rudenko.englishlessonswebapp.model.entity.LessonEntity;
import ry.rudenko.englishlessonswebapp.model.entity.ThemeEntity;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.repository.LessonRepository;
import ry.rudenko.englishlessonswebapp.repository.ThemeRepository;
import ry.rudenko.englishlessonswebapp.repository.UserRepository;


@Service
public class LessonService {
  final
  ThemeRepository themeRepository;
  final
  LessonRepository lessonRepository;
  final
  LessonDtoFactory lessonDtoFactory;
  final
  UserRepository userRepository;

  public LessonService(ThemeRepository themeRepository, LessonRepository lessonRepository,
      LessonDtoFactory lessonDtoFactory,
      UserRepository userRepository) {
    this.themeRepository = themeRepository;
    this.lessonRepository = lessonRepository;
    this.lessonDtoFactory = lessonDtoFactory;
    this.userRepository = userRepository;
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

  public LessonDto lessonToUser( Long lessonId, Long user_id) {

    LessonEntity lesson = getLessonOrThrowNotFound(lessonId);
    UserEntity user = getUserOrThrowNotFound(user_id);
    lesson.setUser(user);
    lessonRepository.saveAndFlush(lesson);
    return lessonDtoFactory.createLessonDto(lesson);
  }

  private UserEntity getUserOrThrowNotFound(Long user_id) {
    return userRepository
        .findById(user_id)
        .orElseThrow(() ->
            new NotFoundException(String.format("User with id \"%s\" not found.", user_id))
        );
  }

  private LessonEntity getLessonOrThrowNotFound(Long lessonId) {
    return lessonRepository
        .findById(lessonId)
        .orElseThrow(() ->
            new NotFoundException(String.format("Lesson with id \"%s\" not found.", lessonId))
        );
  }
}


