package ry.rudenko.englishlessonswebapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ry.rudenko.englishlessonswebapp.model.entity.AnswerEntity;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
  Optional<AnswerEntity> findById(Long id);
  }