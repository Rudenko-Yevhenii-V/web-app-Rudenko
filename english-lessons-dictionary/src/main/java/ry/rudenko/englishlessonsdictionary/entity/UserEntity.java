package ry.rudenko.englishlessonsdictionary.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ry.rudenko.englishlessonsdictionary.exception.NotFoundException;

@Getter
@Setter
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users_entity")
public class UserEntity implements Serializable {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false, updatable = false)
  Long id;

  @Column(name = "name")
  String name;

  @Column(name = "email", unique = true)
  String email;

  @ManyToMany(cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  })
  @JoinTable(name = "word_user",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "word_id")
  )
  private List<WordEntity> wordEntities = new ArrayList<>();

  public void addWord(WordEntity word) {
    if (word != null) {
      wordEntities.add(word);
      word.getUserEntities().add(this);
    }else {
      throw new NotFoundException(" word not find in database");
    }
  }

  public UserEntity(Long id, String name, String email,
      List<WordEntity> wordEntities) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.wordEntities = wordEntities;
  }

  public UserEntity(String name, String email) {
    this.name = name;
    this.email = email;
  }
}



