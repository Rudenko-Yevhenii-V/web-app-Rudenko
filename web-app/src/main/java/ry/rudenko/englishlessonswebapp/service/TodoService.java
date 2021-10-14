package ry.rudenko.englishlessonswebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.model.dto.TodoDto;
import ry.rudenko.englishlessonswebapp.model.entity.TodoEntity;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.repository.TodoRepo;
import ry.rudenko.englishlessonswebapp.repository.UserRepo;

@Service
public class TodoService {
  @Autowired
  private TodoRepo todoRepo;
  @Autowired
  private UserRepo userRepo;

  public TodoDto createTodo(TodoEntity todo, Long userId) {
    UserEntity user = userRepo.findById(userId).get();
    todo.setUser(user);
    return TodoDto.toDto(todoRepo.save(todo));
  }

  public TodoDto complete(Long id) {
    TodoEntity todo = todoRepo.findById(id).get();
    todo.setCompleted(!todo.getCompleted());
    return TodoDto.toDto(todoRepo.save(todo));
  }
}