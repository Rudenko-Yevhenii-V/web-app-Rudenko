package ry.rudenko.englishlessonswebapp.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "answer")
public class AnswerEntity  implements Serializable {

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

  public static AnswerEntity makeDefault(String falseAnswer, Integer answerOrder, QuestionEntity questionEntity) {

    return builder()
        .question(questionEntity)
        .text(falseAnswer)
        .answerOrder(answerOrder)
        .build();
  }
}