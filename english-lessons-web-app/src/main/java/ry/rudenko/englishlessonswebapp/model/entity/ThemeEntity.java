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
@Table(name = "theme")
public class ThemeEntity   implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  @NonNull
  String name;

  @Builder.Default
  @OneToMany
  @JoinColumn(name = "theme_id", referencedColumnName = "id")
  List<LessonEntity> lessonEntities = new ArrayList<>();

  public static ThemeEntity makeDefault(String schoolName) {
    return builder()
        .name(schoolName)
        .build();
  }
}