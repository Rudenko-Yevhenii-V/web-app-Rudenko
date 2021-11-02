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

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class TestController {

  TestRepository testRepository;

  UserRepository userRepository;

  TestUserRepository testUserRepository;

  LessonRepository lessonRepository;

  AdminRepository adminRepository;

  TestDtoFactory testDtoFactory;

  AnswerRepository answerRepository;

  public static final String FETCH_TESTS = "/tests";
  public static final String GET_TEST = "/tests/{testId}";
  public static final String CREATE_OR_UPDATE_TEST = "/tests";
  public static final String DELETE_TEST = "/tests/{testId}";
  public static final String COMPLETE_TEST = "/themes/lessons/{lessonId}/users/{userId}/tests/{testId}/admins/{adminId}/compete";

  @GetMapping(FETCH_TESTS)
  public ResponseEntity<List<TestDto>> fetchTests(@RequestParam(defaultValue = "") String filter) {

    boolean isFiltered = !filter.trim().isEmpty();

    List<TestEntity> tests = testRepository.findAllByFilter(isFiltered, filter);

    return ResponseEntity.ok(testDtoFactory.createTestDtoList(tests));
  }

  @GetMapping(GET_TEST)
  public ResponseEntity<TestDto> getTest(@PathVariable Long testId) {

    TestEntity test = testRepository
        .findById(testId)
        .orElseThrow(() ->
            new NotFoundException(String.format("Test with id  \"%s\" not found.", testId))
        );

    return ResponseEntity.ok(testDtoFactory.createTestDto(test));
  }

  @PostMapping(CREATE_OR_UPDATE_TEST)
  public ResponseEntity<TestDto> createOrUpdateTest(@RequestParam String falseAnswers,
      @RequestParam Integer answerOrder,
      @RequestBody TestDto test) {

    List<String> falseAnswerList = Arrays.stream(falseAnswers.split(","))
        .filter(it -> !it.trim().isEmpty())
        .collect(Collectors.toList());
    TestEntity testEntity = convertTestToEntity(test);
    falseAnswerList.stream().forEach(s -> {
      answerRepository.saveAndFlush(AnswerEntity.makeDefault(s, answerOrder));
    });

    testEntity = testRepository.saveAndFlush(testEntity);

    return ResponseEntity.ok(testDtoFactory.createTestDto(testEntity));
  }

  @DeleteMapping(DELETE_TEST)
  public ResponseEntity<AckDto> deleteTest(
      @PathVariable Long testId) {

    testRepository
        .findById(testId)
        .ifPresent(test -> {

          test.getQuestions().forEach(it -> it.getAnswers().clear());
          test.getQuestions().clear();

          test = testRepository.saveAndFlush(test);

          testRepository.delete(test);
        });

    return ResponseEntity.ok(AckDto.makeDefault(true));
  }

  @PostMapping(COMPLETE_TEST)
  public ResponseEntity<AckDto> completeTest(
      @PathVariable Long lessonId,
      @PathVariable Long testId,
      @PathVariable Long userId,
      @PathVariable Long adminId,
      @RequestParam String answers) {

    TestEntity test = getTestOrThrowNotFound(testId);

    List<String> answerList = Arrays.stream(answers.split(","))
        .filter(it -> !it.trim().isEmpty())
        .collect(Collectors.toList());

    if (answerList.size() != test.getQuestions().size()) {
      throw new BadRequestException("You haven't done all qestions");
    }

    lessonRepository.findById(lessonId).orElseThrow(() ->
            new NotFoundException(String.format("Lesson with id  \"%s\" not found.", lessonId))
        );

    UserEntity user = userRepository
        .findById(userId)
        .orElseThrow(() ->
            new NotFoundException(String.format("User with id  \"%s\" not found.", userId))
        );

    Admin admin = adminRepository
        .findById(adminId)
        .orElseThrow(() ->
            new NotFoundException(String.format("Admin with id  \"%s\" not found.", adminId))
        );

    testUserRepository.saveAndFlush(
        TestUserEntity.builder()
            .answers(answers)
            .user(user)
            .test(test)
            .admin(admin)
            .build()
    );

    return ResponseEntity.ok(AckDto.makeDefault(true));
  }

  private TestEntity getTestOrThrowNotFound(Long testId) {
    return testRepository
        .findById(testId)
        .orElseThrow(() ->
            new NotFoundException(String.format("Test with id  \"%s\" not found.", testId))
        );
  }

  private TestEntity convertTestToEntity(TestDto dto) {
    Long testId = dto.getId();
    TestEntity test;
    if (testId == null) {
      test = TestEntity.makeDefault();
    } else {
      test = testRepository
          .findById(testId)
          .orElseThrow(() ->
              new NotFoundException(String.format("Test with id  \"%s\" not found.", testId))
          );
    }
    test.setName(dto.getName());
    test.getQuestions().clear();
    test.getQuestions().addAll(
        dto.getQuestions()
            .stream()
            .map(this::convertQuestionToEntity)
            .collect(Collectors.toList())
    );
    return test;
  }

  private QuestionEntity convertQuestionToEntity(QuestionDto dto) {
    QuestionEntity question = QuestionEntity.makeDefault();
    question.setId(dto.getId());
    question.setQuestionOrder(dto.getOrder());
    question.setText(dto.getText());
    question.getAnswers().clear();
    question.getAnswers().addAll(
        dto.getAnswers()
            .stream()
            .map(this::convertAnswerToEntity)
            .collect(Collectors.toList())
    );
    return question;
  }

  private AnswerEntity convertAnswerToEntity(AnswerDto dto) {
    AnswerEntity answer = AnswerEntity.makeDefault(dto.getName(), dto.getOrder());
    answer.setId(dto.getId());
    return answer;
  }
}