package ry.rudenko.englishlessonsdictionary.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonsdictionary.access.AccessFilter;
import ry.rudenko.englishlessonsdictionary.entity.dto.CurrentUser;
import ry.rudenko.englishlessonsdictionary.entity.UserEntity;
import ry.rudenko.englishlessonsdictionary.entity.WordEntity;
import ry.rudenko.englishlessonsdictionary.entity.dto.UserDto;
import ry.rudenko.englishlessonsdictionary.entity.dto.WordDto;
import ry.rudenko.englishlessonsdictionary.exception.NotFoundException;
import ry.rudenko.englishlessonsdictionary.exception.UnauthorizedException;
import ry.rudenko.englishlessonsdictionary.factory.UserDtoFactory;
import ry.rudenko.englishlessonsdictionary.repository.UserEntityRepository;
import ry.rudenko.englishlessonsdictionary.repository.WordEntityRepository;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@Validated
@Transactional
@RequestMapping(path = "api/v1/client")
public class UserController {

  UserEntityRepository userEntityRepository;
  WordEntityRepository wordEntityRepository;
  UserDtoFactory userDtoFactory;
  AccessFilter accessFilter;

  @PostMapping("/users/words")
  public ResponseEntity<UserDto> AddWordsToUser(@RequestBody List<Long> wordsId) {

    UserEntity userEntity;
    final CurrentUser currentUser = accessFilter.getCurrentUser();
    if (currentUser.getName() == null) {
      throw new UnauthorizedException("currentUser is null! Or access is Forbidden!");
    }
    final UserEntity userEntityByEmail = userEntityRepository.findUserEntityByEmail(
        currentUser.getEmail());
    userEntity = Objects.requireNonNullElseGet(userEntityByEmail,
        () ->
           userEntityRepository.save(new UserEntity(currentUser.getName(),
                  currentUser.getEmail()))
        );
    for (Long wordId : wordsId) {
      userEntity.addWord(wordEntityRepository.findWordEntityById(wordId));
    }
    return ResponseEntity.ok(userDtoFactory.createUserDto(userEntity));
  }

  @GetMapping("/users/words")
  public ResponseEntity<?> findAllWordsByUserEntities() {
    final CurrentUser currentUser = accessFilter.getCurrentUser();
    if (currentUser.getEmail() == null) {
      throw new UnauthorizedException("current user not found! Or access is Forbidden!");
    }
    UserEntity userEntityByEmail = userEntityRepository.findUserEntityByEmail(currentUser.getEmail());
    if(userEntityByEmail == null){
      return ResponseEntity.ok("First add words!");
    }
    final List<WordEntity> allByUserEntities = wordEntityRepository.findWordEntitiesByUserEntities(
            userEntityByEmail);
    List<WordDto> wordDtoList = new ArrayList<>();
    allByUserEntities.forEach(wordEntity -> wordDtoList.add(new WordDto(
            wordEntity.getId(),
        wordEntity.getWord(),
        wordEntity.getDescription()
    )));
    return ResponseEntity.ok(wordDtoList);
  }

}
