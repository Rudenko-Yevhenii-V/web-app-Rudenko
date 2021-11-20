package ry.rudenko.englishlessonswebapp.service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.auth.bean.RoleRequest;
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

  public UserDto updateUser(UserDto userDto,
      UserEntity principal) {

    Optional<UserEntity> byEmail = userRepository.findByEmail(principal.getEmail());
    UserEntity appUser = byEmail.orElseThrow(() -> new NotFoundException("User not found"));
    appUser.setName(userDto.getName());
    appUser.setMiddleName(userDto.getMiddleName());
    appUser.setLastName(userDto.getLastName());
    appUser.setBirthday(userDto.getBirthday().atStartOfDay().toInstant(ZoneOffset.UTC));
    UserEntity user = userRepository.save(appUser);
    return userDtoFactory.createUserDto(user);
  }

  public AckDto deleteUser(Long userId) {
    if (userRepository.existsById(userId)) {
      userRepository.deleteById(userId);
    }
    return AckDto.makeDefault(true);
  }

    public UserEntity setRole(RoleRequest roleRequest) {
      Optional<UserEntity> byId = userRepository.findById(roleRequest.getId());
      if (byId.isEmpty()){
        throw new NotFoundException(String.format("user with id=%d not found!", roleRequest.getId()));
      }
      UserEntity userEntity = byId.get();
      userEntity.setRole(roleRequest.getRole());
      return userRepository.save(userEntity);
    }
}










