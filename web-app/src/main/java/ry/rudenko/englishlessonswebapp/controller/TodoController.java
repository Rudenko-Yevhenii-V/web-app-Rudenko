package ry.rudenko.englishlessonswebapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonswebapp.model.entity.TodoEntity;
import ry.rudenko.englishlessonswebapp.service.TodoService;

@RestController
@RequestMapping("/todos")
public class TodoController {
  @Autowired
  private TodoService todoService;

  @PostMapping
  public ResponseEntity createTodo(@RequestBody TodoEntity todo,
      @RequestParam Long userId) {
    try {
      return ResponseEntity.ok(todoService.createTodo(todo, userId));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Произошла ошибка");
    }
  }

  @PutMapping
  public ResponseEntity completeTodo(@RequestParam Long id) {
    try {
      return ResponseEntity.ok(todoService.complete(id));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Произошла ошибка");
    }
  }
}