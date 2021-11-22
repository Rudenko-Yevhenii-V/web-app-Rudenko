package ry.rudenko.englishlessonswebapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ry.rudenko.englishlessonswebapp.auth.bean.RoleRequest;
import ry.rudenko.englishlessonswebapp.enums.UserRole;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.UserDto;
import ry.rudenko.englishlessonswebapp.model.entity.LessonEntity;
import ry.rudenko.englishlessonswebapp.model.entity.ThemeEntity;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.repository.LessonRepository;
import ry.rudenko.englishlessonswebapp.repository.ThemeRepository;
import ry.rudenko.englishlessonswebapp.repository.UserEntityRepository;

@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserServiceTest {

  @Autowired
  UserEntityRepository userEntityRepository;
  @Autowired
  UserService userService;
  @Autowired
  LessonRepository lessonRepository;
  @Autowired
  ThemeRepository themeRepository;
  UserEntity userEntity;


  @BeforeEach
  void init() {
    themeRepository.deleteAll();
    lessonRepository.deleteAll();
    userEntityRepository.deleteAll();

    ThemeEntity themeEntity = themeRepository.saveAndFlush(ThemeEntity.makeDefault("Test theme!"));
    LessonEntity save = lessonRepository.save(
        LessonEntity.makeDefault("some text", themeEntity));
    UserEntity userEntity = UserEntity.makeDefault("fn", "mn", "last name", Instant.now(),
        UserRole.ADMIN);
    userEntity.setEmail("test1@gmail.com");
    userEntity.setPassword("test1@gmail.com");
    userEntity.setLessons(save);
    this.userEntity = userEntityRepository.save(userEntity);

  }

  @Order(1)
  @Test
  void createUserDtoListServ() {
    List<UserEntity> test = userService.createUserDtoListServ("last");
    Assertions.assertEquals(test.size(), 1);

  }

  @Order(2)
  @Test
  void setRole() {
    RoleRequest roleRequest = new RoleRequest();
    roleRequest.setRole(UserRole.ADMIN);
    roleRequest.setId(userEntity.getId());
    UserEntity userEntity = userService.setRole(roleRequest);
    assertEquals(userEntity.getRole(), UserRole.ADMIN);
  }

  @Order(3)
  @Test
  void updateUser() {
    UserDto userDto = UserDto.builder()
        .birthday(LocalDate.now())
        .lastName("updated last name")
        .middleName("Updated mn")
        .name("updated name")
        .build();
    userService.updateUser(userDto, userEntity);
    Optional<UserEntity> byEmail = userEntityRepository.findByEmail("test1@gmail.com");
    UserEntity userEntity = byEmail.get();
    assertEquals("updated name", userEntity.getName());
  }

  @Order(4)
  @Test
  void deleteUser() {
    AckDto ackDto = userService.deleteUser(userEntity.getId());
    Assertions.assertTrue(ackDto.getAnswer());
    Assertions.assertTrue(userEntityRepository.findById(userEntity.getId()).isEmpty());
  }

}