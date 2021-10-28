package ry.rudenko.englishlessonswebapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
  UserEntity findByFirstName(String firstName);
}
