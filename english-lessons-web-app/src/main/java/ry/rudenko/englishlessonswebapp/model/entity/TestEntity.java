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
@Table(name = "test")
public class TestEntity   implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  @Column(length = 10485760)
  String name;

  @Builder.Default
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "test_id", referencedColumnName = "id")
  List<QuestionEntity> questions = new ArrayList<>();

  public static TestEntity makeDefault(String name, List<QuestionEntity> questions) {
    return builder()
        .name(name)
        .questions(questions)
        .build();
  }
}
