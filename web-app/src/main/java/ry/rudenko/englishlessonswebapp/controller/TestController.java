package ry.rudenko.englishlessonswebapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ry.rudenko.englishlessonswebapp.exception.BadRequestException;
import ry.rudenko.englishlessonswebapp.exception.NotFoundException;
import ry.rudenko.englishlessonswebapp.factory.TestDtoFactory;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.AnswerDto;
import ry.rudenko.englishlessonswebapp.model.dto.QuestionDto;
import ry.rudenko.englishlessonswebapp.model.dto.TestDto;
import ry.rudenko.englishlessonswebapp.model.entity.Admin;
import ry.rudenko.englishlessonswebapp.model.entity.AnswerEntity;
import ry.rudenko.englishlessonswebapp.model.entity.QuestionEntity;
import ry.rudenko.englishlessonswebapp.model.entity.TestEntity;
import ry.rudenko.englishlessonswebapp.model.entity.TestUserEntity;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.repository.AdminRepository;
import ry.rudenko.englishlessonswebapp.repository.AnswerRepository;
import ry.rudenko.englishlessonswebapp.repository.LessonRepository;
import ry.rudenko.englishlessonswebapp.repository.TestRepository;
import ry.rudenko.englishlessonswebapp.repository.TestUserRepository;
import ry.rudenko.englishlessonswebapp.repository.UserRepository;
import ry.rudenko.englishlessonswebapp.service.TestService;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class TestController {

  TestService testService;

  public static final String FETCH_TESTS = "/tests";
  public static final String GET_TEST = "/tests/{testId}";
  public static final String CREATE_OR_UPDATE_TEST = "/tests";
  public static final String DELETE_TEST = "/tests/{testId}";
  public static final String COMPLETE_TEST = "/themes/lessons/{lessonId}/users/{userId}/tests/{testId}/admins/{adminId}/compete";

  @GetMapping(FETCH_TESTS)
  public ResponseEntity<List<TestDto>> fetchTests(@RequestParam(defaultValue = "") String filter) {
    return ResponseEntity.ok(testService.createTestDtoList(filter));
  }

  @GetMapping(GET_TEST)
  public ResponseEntity<TestDto> getTest(@PathVariable Long testId) {
    return ResponseEntity.ok(testService.createTestDto(testId));
  }

  @PostMapping(CREATE_OR_UPDATE_TEST)
  public ResponseEntity<TestDto> createOrUpdateTest(@RequestParam String falseAnswers,
      @RequestParam Integer answerOrder,
      @RequestBody TestDto test) {
    return ResponseEntity.ok(testService.createTestDtoServ(falseAnswers, answerOrder, test));
  }

  @DeleteMapping(DELETE_TEST)
  public ResponseEntity<AckDto> deleteTest(
      @PathVariable Long testId) {
    return ResponseEntity.ok(testService.deleteTest(testId));
  }

  @PostMapping(COMPLETE_TEST)
  public ResponseEntity<AckDto> completeTest(
      @PathVariable Long lessonId,
      @PathVariable Long testId,
      @PathVariable Long userId,
      @PathVariable Long adminId,
      @RequestParam String answers) {
    return ResponseEntity.ok(testService.completeTest(lessonId, testId, userId, adminId, answers));
  }
}