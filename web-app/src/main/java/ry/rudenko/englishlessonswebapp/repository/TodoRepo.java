package ry.rudenko.englishlessonswebapp.repository;

import org.springframework.data.repository.CrudRepository;
import ry.rudenko.englishlessonswebapp.model.entity.TodoEntity;

public interface TodoRepo extends CrudRepository<TodoEntity, Long> {
}
