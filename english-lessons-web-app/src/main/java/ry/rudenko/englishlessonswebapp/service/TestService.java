package ry.rudenko.englishlessonswebapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.exception.BadRequestException;
import ry.rudenko.englishlessonswebapp.exception.NotFoundException;
import ry.rudenko.englishlessonswebapp.factory.TestDtoFactory;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.AnswerDto;
import ry.rudenko.englishlessonswebapp.model.dto.QuestionDto;
import ry.rudenko.englishlessonswebapp.model.dto.TestDto;
import ry.rudenko.englishlessonswebapp.model.entity.AnswerEntity;
import ry.rudenko.englishlessonswebapp.model.entity.QuestionEntity;
import ry.rudenko.englishlessonswebapp.model.entity.TestEntity;
import ry.rudenko.englishlessonswebapp.model.entity.TestUserEntity;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.repository.AnswerRepository;
import ry.rudenko.englishlessonswebapp.repository.LessonRepository;
import ry.rudenko.englishlessonswebapp.repository.TestRepository;
import ry.rudenko.englishlessonswebapp.repository.TestUserRepository;
import ry.rudenko.englishlessonswebapp.repository.UserEntityRepository;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class TestService {

   TestRepository testRepository;
   TestDtoFactory testDtoFactory;
   AnswerRepository answerRepository;
   UserEntityRepository userRepository;
   TestUserRepository testUserRepository;
   LessonRepository lessonRepository;

  public List<TestDto> createTestDtoList(String filter) {
    boolean isFiltered = !filter.trim().isEmpty();
    List<TestEntity> tests = testRepository.findAllByFilter(isFiltered, filter);
    return testDtoFactory.createTestDtoList(tests);
  }

  public TestDto createTestDto(Long testId) {
    TestEntity test = testRepository
        .findById(testId)
        .orElseThrow(() ->
            new NotFoundException(String.format("Test with id  \"%s\" not found.", testId))
        );
    return testDtoFactory.createTestDto(test);
  }

  public TestDto createTestDtoServ(String falseAnswers, Integer answerOrder, TestDto test) {

    List<String> falseAnswerList = Arrays.stream(falseAnswers.split(","))
        .filter(it -> !it.trim().isEmpty())
        .collect(Collectors.toList());
    TestEntity testEntity = convertTestToEntity(test);
    falseAnswerList.forEach(
        s -> answerRepository.saveAndFlush(AnswerEntity.makeDefault(s, answerOrder)));

    testEntity = testRepository.saveAndFlush(testEntity);

    return testDtoFactory.createTestDto(testEntity);
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

  public AckDto deleteTest(Long testId) {
    testRepository
        .findById(testId)
        .ifPresent(test -> {
          test.getQuestions().forEach(it -> it.getAnswers().clear());
          test.getQuestions().clear();
          test = testRepository.saveAndFlush(test);
          testRepository.delete(test);
        });
    return AckDto.makeDefault(true);
  }

  public AckDto completeTest(Long lessonId, Long testId, Long userId, String answers) {
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



    testUserRepository.saveAndFlush(
        TestUserEntity.builder()
            .answers(answers)
            .user(user)
            .test(test)
            .build()
    );

    return AckDto.makeDefault(true);
  }

  private TestEntity getTestOrThrowNotFound(Long testId) {
    return testRepository
        .findById(testId)
        .orElseThrow(() ->
            new NotFoundException(String.format("Test with id  \"%s\" not found.", testId))
        );
  }
}













