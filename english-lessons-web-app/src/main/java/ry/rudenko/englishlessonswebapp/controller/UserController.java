package ry.rudenko.englishlessonswebapp.controller;

import java.util.List;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonswebapp.Routes;
import ry.rudenko.englishlessonswebapp.auth.bean.RoleRequest;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.UserDto;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.service.UserService;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@Validated
@Transactional
@RequestMapping(Routes.API_ROOT)
public class UserController {

  UserService userService;

  @GetMapping(Routes.FETCH_USERS_BY_THEME)
  public ResponseEntity<List<UserDto>> fetchUsersByLesson(
      @RequestParam(defaultValue = "") String filterLastName,
      @PathVariable Long lessonId) {
    return ResponseEntity.ok(userService.createUserDtoList(filterLastName, lessonId));
  }

  @GetMapping("/admin/" +Routes.FETCH_USERS)
  public ResponseEntity<List<UserEntity>> fetchUsers(
      @RequestParam(defaultValue = "") String filterLastName) {
    return ResponseEntity.ok(userService.createUserDtoListServ(filterLastName));
  }

  @PutMapping(Routes.UPDATE_USER)
  public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
    UserEntity principal = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(userService.updateUser(userDto, principal));
  }

  @PutMapping("/admin" + Routes.UPDATE_USER_ROLE)
  public ResponseEntity<UserEntity> updateUserAdmin(@RequestBody RoleRequest roleRequest) {

    return ResponseEntity.ok(userService.setRole(roleRequest));
  }

  @DeleteMapping("/admin" + Routes.DELETE_USER)
  public ResponseEntity<AckDto> deleteUser(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.deleteUser(userId));
  }

}




















