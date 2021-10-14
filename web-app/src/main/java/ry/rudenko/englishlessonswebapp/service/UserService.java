package ry.rudenko.englishlessonswebapp.service;

import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.model.dto.UserDto;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.exception.UserAlreadyExistException;
import ry.rudenko.englishlessonswebapp.exception.UserNotFoundException;
import ry.rudenko.englishlessonswebapp.repository.UserRepo;

@Service
public class UserService {

  private final UserRepo userRepo;

  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  public UserEntity registration(UserEntity user) throws UserAlreadyExistException {
    if (userRepo.findByUsername(user.getUsername()) != null) {
      throw new UserAlreadyExistException("User with this name already exist");
    }
    return userRepo.save(user);
  }

  public UserDto getOneUser(Long id) throws UserNotFoundException {
    UserEntity user = userRepo.findById(id).get();
    if (user == null) {
      throw new UserNotFoundException("User not found");
    }
    return UserDto.toDto(user);
  }

  public Long delete(Long id)  {
      userRepo.deleteById(id);
    return id;
  }
}










