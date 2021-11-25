package ry.rudenko.englishlessonswebapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.auth.bean.CreateTestRequest;
import ry.rudenko.englishlessonswebapp.exception.NotFoundException;
import ry.rudenko.englishlessonswebapp.factory.TestDtoFactory;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.TestDto;
import ry.rudenko.englishlessonswebapp.model.entity.AnswerEntity;
import ry.rudenko.englishlessonswebapp.model.entity.QuestionEntity;
import ry.rudenko.englishlessonswebapp.model.entity.TestEntity;
import ry.rudenko.englishlessonswebapp.model.entity.TestUserEntity;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.repository.AnswerRepository;
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

  public TestDto createTestDtoServ(CreateTestRequest createTestRequest) {
    List<AnswerEntity> answerEntities = new ArrayList<>();
    answerEntities.add(
        new AnswerEntity(createTestRequest.getAnswerOrder(), createTestRequest.getAnswerText()));
    answerEntities.add(new AnswerEntity(createTestRequest.getFalseAnswerText1()));
    answerEntities.add(new AnswerEntity(createTestRequest.getFalseAnswerText2()));
    answerEntities.add(new AnswerEntity(createTestRequest.getFalseAnswerText3()));
    List<QuestionEntity> questions = new ArrayList<>();
    questions.add(QuestionEntity.makeDefault(
        createTestRequest.getQuestionOrder(),
        createTestRequest.getQuestionText(),
        answerEntities
    ));
    TestEntity testEntity = TestEntity.makeDefault(createTestRequest.getTestName(), questions);
    testEntity = testRepository.saveAndFlush(testEntity);
    return testDtoFactory.createTestDto(testEntity);
  }

//  private TestEntity convertTestToEntity(TestDto dto) {
//    Long testId = dto.getId();
//    TestEntity test;
//    if (testId == null) {
//      test = new TestEntity();
//    } else {
//      test = testRepository
//          .findById(testId)
//          .orElseThrow(() ->
//              new NotFoundException(String.format("Test with id  \"%s\" not found.", testId))
//          );
//    }
//    test.setName(dto.getName());
//    test.getQuestions().clear();
//    test.getQuestions().addAll(
//        dto.getQuestions()
//            .stream()
//            .map(this::convertQuestionToEntity)
//            .collect(Collectors.toList())
//    );
//    return test;
//  }

//  private QuestionEntity convertQuestionToEntity(QuestionDto dto) {
//    QuestionEntity question = new QuestionEntity();
//    question.setId(dto.getId());
//    question.setQuestionOrder(dto.getOrder());
//    question.setText(dto.getText());
//    question.getAnswers().clear();
//    question.getAnswers().addAll(
//        dto.getAnswers()
//            .stream()
//            .map(this::convertAnswerToEntity)
//            .collect(Collectors.toList())
//    );
//    return question;
//  }

//  private AnswerEntity convertAnswerToEntity(AnswerDto dto) {
//    AnswerEntity answer = new AnswerEntity();
//    answer.setText(dto.getName());
//    answer.setAnswerOrder(dto.getOrder());
//    answer.setId(dto.getId());
//    return answer;
//  }

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

  public AckDto completeTest(Long testId, Long userId, Long answer) {
    TestEntity test = getTestOrThrowNotFound(testId);
    List<QuestionEntity> questions = test.getQuestions();
    QuestionEntity questionEntity = questions.get(0);
    List<AnswerEntity> answers = questionEntity.getAnswers();
    Optional<AnswerEntity> byIdAnswer = answerRepository.findById(answer);
    UserEntity user = userRepository
        .findById(userId)
        .orElseThrow(() ->
            new NotFoundException(String.format("User with id  \"%s\" not found.", userId))
        );
    testUserRepository.saveAndFlush(
        TestUserEntity.builder()
            .answer(byIdAnswer.orElseThrow(() -> new NotFoundException("byIdAnswer null")))
            .user(user)
            .test(test)
            .build()
    );
    AnswerEntity byIdAnswer_null = byIdAnswer.orElseThrow(() ->
        new NotFoundException("byIdAnswer null"));

    return AckDto.makeDefault(
        answers.get(0).getAnswerOrder().equals(byIdAnswer_null.getAnswerOrder()));
  }

  private TestEntity getTestOrThrowNotFound(Long testId) {
    return testRepository
        .findById(testId)
        .orElseThrow(() ->
            new NotFoundException(String.format("Test with id  \"%s\" not found.", testId))
        );
  }
}













