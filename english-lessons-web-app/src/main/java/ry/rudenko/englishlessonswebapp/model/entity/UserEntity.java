package ry.rudenko.englishlessonswebapp.model.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.time.Instant;
import ry.rudenko.englishlessonswebapp.enums.UserRole;
import ry.rudenko.englishlessonswebapp.model.dto.LessonDto;
import ry.rudenko.englishlessonswebapp.model.dto.TodoDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users_entity")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  Long id;

  @NonNull
  @Column(unique = true)
  String login;

  @NonNull
  String password;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  List<TodoEntity> todos;

  @NonNull
  String firstName;

  @NonNull
  String lastName;

  String middleName;

  @NonNull
  Instant birthday;

  @NonNull
  @Enumerated(EnumType.STRING)
  UserRole role;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  List<LessonEntity> lessons;

  public List<TodoDto> getTodoList() {
    List<TodoDto> todoDtos = new ArrayList<>();
    if(todos != null) {
      for (TodoEntity todo : todos) {
        todoDtos.add(TodoDto.toDto(todo));
      }
    }
    return todoDtos;
  }
  public List<LessonDto> getLessonList() {
    List<LessonDto> lessonDtos = new ArrayList<>();
    if (lessons != null) {
      for (LessonEntity lessonEntity : lessons) {
        lessonDtos.add(LessonDto.toDto(lessonEntity));
      }
    }
    return lessonDtos;
  }

  public static UserEntity makeDefault(
      String firstName,
      String middleName,
      String lastName,
      String login,
      String password,
      Instant birthday,
      UserRole role) {
    return builder()
        .firstName(firstName)
        .middleName(middleName)
        .lastName(lastName)
        .login(login)
        .password(password)
        .birthday(birthday)
        .role(role)
        .build();
  }
}














