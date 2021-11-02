package ry.rudenko.englishlessonswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import ry.rudenko.englishlessonswebapp.model.entity.TestUserEntity;

public interface TestUserRepository extends JpaRepository<TestUserEntity, Long> {

  @Query("SELECT tu FROM TestUserEntity tu WHERE tu.test.id =:testId")
  List<TestUserEntity> findAllByTestIdAndPsychologistId(Long testId);
}
