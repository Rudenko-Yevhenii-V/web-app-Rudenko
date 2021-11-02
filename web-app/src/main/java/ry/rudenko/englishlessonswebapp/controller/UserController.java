package ry.rudenko.englishlessonswebapp.controller;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ry.rudenko.englishlessonswebapp.enums.UserRole;
import ry.rudenko.englishlessonswebapp.exception.NotFoundException;
import ry.rudenko.englishlessonswebapp.factory.UserDtoFactory;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.UserDto;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.repository.UserRepository;
import ry.rudenko.englishlessonswebapp.util.StringChecker;

@Log4j2
@RequiredArgsConstructor
@ExtensionMethod(StringChecker.class)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class UserController {

  UserRepository userRepository;
  UserDtoFactory userDtoFactory;
  public static final String FETCH_USERS = "/users";
  public static final String FETCH_USERS_BY_THEME = "/{lessonId}/users";
  public static final String CREATE_USER = "/users/create";
  public static final String DELETE_USER = "/users/{userId}";
  public static final String GET_USER_ID_BY_LOGIN_AND_PASSWORD = "/user/get_user_id";
  private static final int PASSWORD_LENGTH = 10;

  @GetMapping(FETCH_USERS_BY_THEME)
  public ResponseEntity<List<UserDto>> fetchUsersByLesson(
      @RequestParam(defaultValue = "") String filterLastName,
      @PathVariable Long lessonId) {


    boolean isFiltered = !filterLastName.trim().isEmpty();

    List<UserEntity> users = userRepository.findAllByFilterAndLessonEntityId(isFiltered, filterLastName, lessonId);

    return ResponseEntity.ok(userDtoFactory.createUserDtoList(users));
  }

  @GetMapping(FETCH_USERS)
  public ResponseEntity<List<UserDto>> fetchUsers(
      @RequestParam(defaultValue = "") String filterLastName) {

    boolean isFiltered = !filterLastName.trim().isEmpty();

    List<UserEntity> users = userRepository.findAllByFilter(isFiltered, filterLastName);

    return ResponseEntity.ok(userDtoFactory.createUserDtoList(users));
  }

  @PostMapping(CREATE_USER)
  public ResponseEntity<UserDto> createUser(
      @RequestParam Instant birthday,
      @RequestParam String firstName,
      @RequestParam(defaultValue = "") String middleName,
      @RequestParam String lastName,
      @RequestParam UserRole userRole) {

    firstName = firstName.trim();
    lastName = lastName.trim();

    firstName.checkOnEmpty("firstName");
    lastName.checkOnEmpty("lastName");
    middleName = middleName.trim().isEmpty() ? null : middleName;

    String login = makeLogin(firstName, lastName);
    String password = makePassword();

    UserEntity user = userRepository.saveAndFlush(
        UserEntity.makeDefault(
            firstName,
            middleName,
            lastName,
            login,
            password,
            birthday,
            userRole
        )
    );

    return ResponseEntity.ok(userDtoFactory.createUserDto(user));
  }

  @DeleteMapping(DELETE_USER)
  public ResponseEntity<AckDto> deleteUser(
      @PathVariable Long userId) {

    if (userRepository.existsById(userId)) {
      userRepository.deleteById(userId);
    }

    return ResponseEntity.ok(AckDto.makeDefault(true));
  }

  @GetMapping(GET_USER_ID_BY_LOGIN_AND_PASSWORD)
  public ResponseEntity<Long> getUserIdByLoginAndPassword(
      @RequestParam String login,
      @RequestParam String password) {

    UserEntity user = userRepository
        .findTopByLoginAndPassword(login, password)
        .orElseThrow(() -> new NotFoundException("User with this log and pass exist!"));

    return ResponseEntity.ok(user.getId());
  }

  private String makeLogin(String firstName, String lastName) {

    String firstNameTransliterated = (firstName.toLowerCase());

    String lastNameTransliterated = (lastName.toLowerCase());

    return String.format("%s.%s", firstNameTransliterated.charAt(0), lastNameTransliterated);
  }

  private String makePassword() {

    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, PASSWORD_LENGTH);
  }

}




















