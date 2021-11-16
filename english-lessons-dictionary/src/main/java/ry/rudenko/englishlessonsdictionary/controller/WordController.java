package ry.rudenko.englishlessonsdictionary.controller;

import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ry.rudenko.englishlessonsdictionary.access.AccessFilter;
import ry.rudenko.englishlessonsdictionary.entity.dto.CurrentUser;
import ry.rudenko.englishlessonsdictionary.entity.WordEntity;
import ry.rudenko.englishlessonsdictionary.entity.dto.WordDto;
import ry.rudenko.englishlessonsdictionary.exception.NotFoundException;
import ry.rudenko.englishlessonsdictionary.exception.UnauthorizedException;
import ry.rudenko.englishlessonsdictionary.repository.WordEntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@Validated
@Transactional
@RequestMapping(path = "api/v1/client")
public class WordController {
  WordEntityRepository wordEntityRepository;
  AccessFilter accessFilter;
  @PostMapping("/words")
  public ResponseEntity<WordEntity> AddWord(@RequestBody WordDto wordDto) {
    final CurrentUser currentUser = accessFilter.getCurrentUser();
    if (currentUser.getEmail() == null) {
      throw new UnauthorizedException("current user not found! Or access is Forbidden!");
    }
    final WordEntity wordEntity = WordEntity.makeDefault(wordDto.getWord(),
        wordDto.getDescription());
    WordEntity wordEntityByWord = wordEntityRepository.findWordEntityByWord(wordEntity.getWord());
    return ResponseEntity.ok(Objects.requireNonNullElseGet(wordEntityByWord, () -> wordEntityRepository.save(wordEntity)));
  }

  @GetMapping("/words")
  public ResponseEntity<?> findAllWordsInDictionary() {
    final CurrentUser currentUser = accessFilter.getCurrentUser();
    if (currentUser.getEmail() == null) {
      throw new UnauthorizedException("current user not found! Or access is Forbidden!");
    }
    final List<WordEntity> allByUserEntities = wordEntityRepository.findAll();
    List<WordDto> wordDtos = new ArrayList<>();
    allByUserEntities.stream().forEach(wordEntity -> wordDtos.add(new WordDto(
            wordEntity.getId(), wordEntity.getWord(), wordEntity.getDescription()
    )));
    return ResponseEntity.ok(wordDtos);
  }

}
