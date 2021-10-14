package ry.rudenko.englishlessonswebapp.repository;


import org.springframework.data.repository.CrudRepository;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
  UserEntity findByUsername(String username);
}
