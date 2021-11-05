package ry.rudenko.englishlessonswebapp.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "test")
public class TestEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  @Column(length = 10485760)
  String name;

//  @Builder.Default
//  Boolean isStarted = false;

  @Builder.Default
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "test_id", referencedColumnName = "id")
  List<QuestionEntity> questions = new ArrayList<>();

  public static TestEntity makeDefault() {
    return builder().build();
  }
}