package ry.rudenko.englishlessonswebapp.controller;


import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonswebapp.Routes;
import ry.rudenko.englishlessonswebapp.model.dto.TodoDto;
import ry.rudenko.englishlessonswebapp.service.TodoService;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping(Routes.API_ROOT + "/todos")
public class TodoController {

   TodoService todoService;

  @PostMapping
  public ResponseEntity<?> createTodo(@RequestBody TodoDto tododto,
      @RequestParam Long userId) {
    try {
      return ResponseEntity.ok(todoService.createEntity(tododto, userId));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("An error has occurred! " + e);
    }
  }

  @PutMapping
  public ResponseEntity<?> completeTodo(@RequestParam Long id) {
    try {
      return ResponseEntity.ok(todoService.complete(id));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("An error has occurred!" + e);
    }
  }
}