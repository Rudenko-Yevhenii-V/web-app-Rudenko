package ry.rudenko.englishlessonswebapp.model.entity;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "question")
public class QuestionEntity   implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  Integer questionOrder;

  @Column(length = 10485760)
  String text;

  @ManyToOne
  @JoinColumn(name = "test_id", referencedColumnName = "id")
  TestEntity test;

  @Column(name = "test_id", updatable = false, insertable = false)
  Long testId;

  @Builder.Default
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "question_id", referencedColumnName = "id")
  List<AnswerEntity> answers = new ArrayList<>();

  public static QuestionEntity makeDefault(Integer questionOrder, String text, List<AnswerEntity> answers) {
    return builder()
        .text(text)
        .questionOrder(questionOrder)
        .answers(answers)
        .build();
  }
}
