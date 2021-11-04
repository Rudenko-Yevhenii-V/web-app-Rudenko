package ry.rudenko.englishlessonswebapp.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.LessonDto;
import ry.rudenko.englishlessonswebapp.service.LessonService;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@Transactional
public class LessonController {

  LessonService lessonService;

  public static final String FETCH_LESSONS = "/themes/{themeId}/lessons";
  public static final String CREATE_LESSONS = "/themes/{themeId}/lessons/{lessonsName}";
  public static final String DELETE_LESSON = "/themes/{themeId}/lessons/{lessonId}";
  public static final String ADD_TO_USER_LESSON = "/themes/{themeId}/lessons/{lessonId}/{user_id}";

  @GetMapping(FETCH_LESSONS)
  public ResponseEntity<List<LessonDto>> fetchLessons(@PathVariable Long themeId,
      @RequestParam(defaultValue = "") String prefix) {
    return ResponseEntity.ok(lessonService.createLessonDtoList(themeId, prefix));
  }
  @GetMapping(ADD_TO_USER_LESSON)
  public ResponseEntity<LessonDto> lessonToUser( @PathVariable Long themeId,
      @PathVariable Long lessonId, @PathVariable Long user_id) {
    return ResponseEntity.ok(lessonService.lessonToUser(lessonId, user_id));
  }
  @PostMapping(CREATE_LESSONS)
  public ResponseEntity<LessonDto> createLesson(@PathVariable Long themeId,
      @PathVariable String lessonsName) {
    return ResponseEntity.ok(lessonService.createLessonDto(themeId, lessonsName));
  }

  @DeleteMapping(DELETE_LESSON)
  public ResponseEntity<AckDto> deleteLesson(@PathVariable Long themeId,
      @PathVariable Long lessonId) {
    return ResponseEntity.ok(lessonService.deleteLesson(themeId, lessonId));
  }
}
