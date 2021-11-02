package ry.rudenko.englishlessonswebapp.factory;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import ry.rudenko.englishlessonswebapp.model.dto.AnswerDto;
import ry.rudenko.englishlessonswebapp.model.dto.LiteTestDto;
import ry.rudenko.englishlessonswebapp.model.dto.QuestionDto;
import ry.rudenko.englishlessonswebapp.model.dto.TestDto;
import ry.rudenko.englishlessonswebapp.model.entity.AnswerEntity;
import ry.rudenko.englishlessonswebapp.model.entity.QuestionEntity;
import ry.rudenko.englishlessonswebapp.model.entity.TestEntity;

@Component
public class TestDtoFactory {

  public LiteTestDto createLiteTestDto(TestEntity entity) {
    return LiteTestDto.builder().id(entity.getId()).name(entity.getName()).build();
  }
  public TestDto createTestDto(TestEntity entity) {
    return TestDto.builder().id(entity.getId()).name(entity.getName())
        .questions(createQuestionDtoList(entity.getQuestions())).build();
  }
  public List<TestDto> createTestDtoList(List<TestEntity> entities) {
    return entities.stream().map(this::createTestDto).distinct().collect(Collectors.toList());
  }

  public List<QuestionDto> createQuestionDtoList(List<QuestionEntity> entities) {
    return entities.stream().map(this::createQuestionDto).distinct().collect(Collectors.toList());
  }

  public QuestionDto createQuestionDto(QuestionEntity entity) {
    return QuestionDto.builder().id(entity.getId()).text(entity.getText())
        .order(entity.getQuestionOrder()).answers(createAnswerDtoList(entity.getAnswers()))
        .build();
  }

  public List<AnswerDto> createAnswerDtoList(List<AnswerEntity> entities) {
    return entities.stream().map(this::createAnswerDto).distinct().collect(Collectors.toList());
  }

  public AnswerDto createAnswerDto(AnswerEntity entity) {
    return AnswerDto.builder().id(entity.getId()).name(entity.getName())
        .order(entity.getAnswerOrder()).build();
  }
}
