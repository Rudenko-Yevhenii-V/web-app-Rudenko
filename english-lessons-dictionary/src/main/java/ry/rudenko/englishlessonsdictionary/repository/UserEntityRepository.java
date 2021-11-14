package ry.rudenko.englishlessonsdictionary.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ry.rudenko.englishlessonsdictionary.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findUserEntityByEmail(String email);

}

