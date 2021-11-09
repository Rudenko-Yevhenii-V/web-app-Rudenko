package ry.rudenko.englishlessonswebapp.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.exception.NotFoundException;
import ry.rudenko.englishlessonswebapp.factory.UserDtoFactory;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.UserDto;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.repository.UserEntityRepository;
import ry.rudenko.englishlessonswebapp.util.StringChecker;

@RequiredArgsConstructor
@ExtensionMethod(StringChecker.class)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class UserService {

  UserEntityRepository userRepository;
  UserDtoFactory userDtoFactory;
//  private static final int PASSWORD_LENGTH = 10;


  public List<UserDto> createUserDtoList(String filterLastName, Long lessonId) {
    boolean isFiltered = !filterLastName.trim().isEmpty();
    List<UserEntity> users = userRepository.findAllByFilterAndLessonEntityId(isFiltered,
        filterLastName, lessonId);
    return userDtoFactory.createUserDtoList(users);
  }

  public List<UserDto> createUserDtoListServ(String filterLastName) {
    boolean isFiltered = !filterLastName.trim().isEmpty();
    List<UserEntity> users = userRepository.findAllByFilter(isFiltered, filterLastName);
    return userDtoFactory.createUserDtoList(users);
  }

  public UserDto createUserDto(UserDto userDto) {

    UserEntity user = userRepository.saveAndFlush(
        UserEntity.makeDefault(
            userDto.getFirstName(),
            userDto.getMiddleName(),
            userDto.getLastName(),
            userDto.getLogin(),
            userDto.getPassword(),
            userDto.getBirthday(),
            userDto.getRole()
        )
    );
    return userDtoFactory.createUserDto(user);
  }

//  private String makeLogin(String firstName, String lastName) {
//    String firstNameTransliterated = (firstName.toLowerCase());
//    String lastNameTransliterated = (lastName.toLowerCase());
//    return String.format("%s.%s", firstNameTransliterated.charAt(0), lastNameTransliterated);
//  }

//  private String makePassword() {
//    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, PASSWORD_LENGTH);
//  }

  public AckDto deleteUser(Long userId) {
    if (userRepository.existsById(userId)) {
      userRepository.deleteById(userId);
    }
    return AckDto.makeDefault(true);
  }

  public Long getUserIdByLoginAndPassword(String login, String password) {
    UserEntity user = userRepository
        .findTopByLoginAndPassword(login, password)
        .orElseThrow(() -> new NotFoundException("User with this log and pass exist!"));
    return user.getId();
  }
}










