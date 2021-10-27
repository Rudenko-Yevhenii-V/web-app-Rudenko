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
@Table(name = "school_class")
public class LessonEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  @NonNull
  String name;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "school_id", referencedColumnName = "id")
  ThemeEntity themeEntity;

  @Column(name = "school_id", updatable = false, insertable = false)
  Long schoolId;

  public static LessonEntity makeDefault(String name, ThemeEntity themeEntity) {
    return builder()
        .name(name)
        .themeEntity(themeEntity)
        .build();
  }
}