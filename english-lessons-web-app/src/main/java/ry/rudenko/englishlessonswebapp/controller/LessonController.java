package ry.rudenko.englishlessonswebapp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ry.rudenko.englishlessonswebapp.Routes;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.LessonDto;
import ry.rudenko.englishlessonswebapp.model.dto.PrefixDto;
import ry.rudenko.englishlessonswebapp.model.dto.UserDto;
import ry.rudenko.englishlessonswebapp.service.LessonService;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@Transactional
@RequestMapping(Routes.API_ROOT)
public class LessonController {

  LessonService lessonService;

  @GetMapping(Routes.FETCH_LESSONS)
  public ResponseEntity<List<LessonDto>> fetchLessons(@PathVariable Long themeId,
      @RequestBody PrefixDto prefix) {
    return ResponseEntity.ok(lessonService.createLessonDtoList(themeId, prefix));
  }
  @PostMapping(Routes.ADD_TO_USER_LESSON)
  public ResponseEntity<UserDto> lessonToUser(
      @PathVariable Long lessonId, @PathVariable Long user_id) {
    return ResponseEntity.ok(lessonService.lessonToUser(lessonId, user_id));
  }
  @PostMapping("/admin" + Routes.CREATE_LESSONS)
  public ResponseEntity<LessonDto> createLesson(@PathVariable Long themeId,
      @PathVariable String lessonsText) {

    return ResponseEntity.ok(lessonService.createLessonDto(themeId, lessonsText));
  }

  @DeleteMapping("/admin" + Routes.DELETE_LESSON)
  public ResponseEntity<AckDto> deleteLesson(@PathVariable Long themeId,
      @PathVariable Long lessonId) {
    return ResponseEntity.ok(lessonService.deleteLesson(themeId, lessonId));
  }
}
