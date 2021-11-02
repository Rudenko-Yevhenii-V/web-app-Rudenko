package ry.rudenko.englishlessonswebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.model.dto.TodoDto;
import ry.rudenko.englishlessonswebapp.model.entity.TodoEntity;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.repository.TodoRepo;
import ry.rudenko.englishlessonswebapp.repository.UserRepository;

@Service
public class TodoService {
  @Autowired
  private TodoRepo todoRepo;
  @Autowired
  private UserRepository userRepo;

  public TodoDto createEntity(TodoDto tododto, Long userId) {
    UserEntity user = userRepo.findById(userId).get();
    TodoEntity todo = TodoEntity.makeDefault(
        tododto.getTitle(),
        tododto.getCompleted(),
        tododto.getDescription(),
        user
    );
    return TodoDto.toDto(todoRepo.save(todo));
  }

  public TodoDto complete(Long id) {
    TodoEntity todo = todoRepo.findById(id).get();
    todo.setCompleted(!todo.getCompleted());
    return TodoDto.toDto(todoRepo.save(todo));
  }
}