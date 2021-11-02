package ry.rudenko.englishlessonswebapp.repository;


import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ry.rudenko.englishlessonswebapp.model.entity.AnswerEntity;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
  }