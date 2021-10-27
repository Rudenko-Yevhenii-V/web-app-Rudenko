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
@Table(name = "school")
public class ThemeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  @Column(length = 10485760)
  @NonNull
  String name;

  @Builder.Default
  @OneToMany
  @JoinColumn(name = "school_id", referencedColumnName = "id")
  List<LessonEntity> lessonEntities = new ArrayList<>();

  public static ThemeEntity makeDefault(String schoolName) {
    return builder()
        .name(schoolName)
        .build();
  }
}