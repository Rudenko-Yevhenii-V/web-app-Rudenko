package ry.rudenko.englishlessonsdictionary.entity;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "words")
public class WordEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "word_id")
  Long id;
  String word;
  String description;

@ManyToMany(mappedBy = "wordEntities")
private List<UserEntity> userEntities = new ArrayList<>();

  public static WordEntity makeDefault(
      String word,
      String description) {
    return builder()
        .word(word)
        .description(description)
        .build();
  }
}
