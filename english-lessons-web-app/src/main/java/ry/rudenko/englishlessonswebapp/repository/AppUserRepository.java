package ry.rudenko.englishlessonswebapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;

public interface AppUserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);
}