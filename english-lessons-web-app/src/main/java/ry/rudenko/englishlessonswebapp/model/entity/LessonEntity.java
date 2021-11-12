package ry.rudenko.englishlessonswebapp.model.entity;

import java.io.Serializable;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "lessons")
public class LessonEntity   implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  @NonNull
  @Column(length = 10485760)
  String text;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "theme_id", referencedColumnName = "id")
  ThemeEntity themeEntity;

  @Column(name = "theme_id", updatable = false, insertable = false)
  Long schoolId;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "lessons")
  List<UserEntity> users;

  public static LessonEntity makeDefault(String text, ThemeEntity themeEntity) {
    return builder()
        .text(text)
        .themeEntity(themeEntity)
        .build();
  }
}