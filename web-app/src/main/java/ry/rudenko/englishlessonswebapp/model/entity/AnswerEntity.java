package ry.rudenko.englishlessonswebapp.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "answer")
public class AnswerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  Integer answerOrder;

  @Column(length = 10000000)
  String text;

  @ManyToOne
  @JoinColumn(name = "question_id", referencedColumnName = "id")
  QuestionEntity question;

  @Column(name = "question_id", updatable = false, insertable = false)
  Long questionId;

  public static AnswerEntity makeDefault(String falseAnswer, Integer answerOrder) {

    return builder()
        .text(falseAnswer)
        .answerOrder(answerOrder)
        .build();
  }
}