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
import ry.rudenko.englishlessonswebapp.service.UserService;
import ry.rudenko.englishlessonswebapp.util.StringChecker;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class UserController {

  UserService userService;

  public static final String FETCH_USERS = "/users";
  public static final String FETCH_USERS_BY_THEME = "/{lessonId}/users";
  public static final String CREATE_USER = "/users/create";
  public static final String DELETE_USER = "/users/{userId}";
  public static final String GET_USER_ID_BY_LOGIN_AND_PASSWORD = "/user/get_user_id";

  @GetMapping(FETCH_USERS_BY_THEME)
  public ResponseEntity<List<UserDto>> fetchUsersByLesson(
      @RequestParam(defaultValue = "") String filterLastName,
      @PathVariable Long lessonId) {
    return ResponseEntity.ok(userService.createUserDtoList(filterLastName, lessonId));
  }

  @GetMapping(FETCH_USERS)
  public ResponseEntity<List<UserDto>> fetchUsers(
      @RequestParam(defaultValue = "") String filterLastName) {
    return ResponseEntity.ok(userService.createUserDtoListServ(filterLastName));
  }

  @PostMapping(CREATE_USER)
  public ResponseEntity<UserDto> createUser(
      @RequestParam Instant birthday,
      @RequestParam String firstName,
      @RequestParam(defaultValue = "") String middleName,
      @RequestParam String lastName,
      @RequestParam UserRole userRole) {

    return ResponseEntity.ok(userService.createUserDto(birthday, firstName, middleName, lastName, userRole));
  }

  @DeleteMapping(DELETE_USER)
  public ResponseEntity<AckDto> deleteUser(
      @PathVariable Long userId) {
    return ResponseEntity.ok(userService.deleteUser(userId));
  }

  @GetMapping(GET_USER_ID_BY_LOGIN_AND_PASSWORD)
  public ResponseEntity<Long> getUserIdByLoginAndPassword(
      @RequestParam String login,
      @RequestParam String password) {

    return ResponseEntity.ok(userService.getUserIdByLoginAndPassword(login, password));
  }



}




















