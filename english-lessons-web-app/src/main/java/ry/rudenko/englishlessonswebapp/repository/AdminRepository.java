package ry.rudenko.englishlessonswebapp.repository;


import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import ry.rudenko.englishlessonswebapp.model.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findTopByLoginAndPassword(@NonNull String login, @NonNull String password);
  }