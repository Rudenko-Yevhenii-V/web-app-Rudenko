package ry.rudenko.englishlessonsdictionary.controller;

import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonsdictionary.access.AccessFilter;
import ry.rudenko.englishlessonsdictionary.entity.dto.CurrentUser;
import ry.rudenko.englishlessonsdictionary.entity.WordEntity;
import ry.rudenko.englishlessonsdictionary.entity.dto.WordDto;
import ry.rudenko.englishlessonsdictionary.exception.NotFoundException;
import ry.rudenko.englishlessonsdictionary.repository.WordEntityRepository;

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
    if (currentUser == null){
      throw new NotFoundException("current user not found! Or access is Forbidden!");
    }
    final WordEntity wordEntity = WordEntity.makeDefault(wordDto.getWord(),
        wordDto.getDescription());
    return ResponseEntity.ok(wordEntityRepository.save(wordEntity));
  }

}
