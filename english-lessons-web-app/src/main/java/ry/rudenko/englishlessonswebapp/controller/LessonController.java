package ry.rudenko.englishlessonswebapp.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonswebapp.Routes;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.LessonDto;
import ry.rudenko.englishlessonswebapp.model.dto.PrefixDto;
import ry.rudenko.englishlessonswebapp.model.dto.UserDto;
import ry.rudenko.englishlessonswebapp.service.LessonService;

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
  @PostMapping(Routes.CREATE_LESSONS)
  public ResponseEntity<LessonDto> createLesson(@PathVariable Long themeId,
      @PathVariable String lessonsText) {

    return ResponseEntity.ok(lessonService.createLessonDto(themeId, lessonsText));
  }

  @DeleteMapping(Routes.DELETE_LESSON)
  public ResponseEntity<AckDto> deleteLesson(@PathVariable Long themeId,
      @PathVariable Long lessonId) {
    return ResponseEntity.ok(lessonService.deleteLesson(themeId, lessonId));
  }
}
