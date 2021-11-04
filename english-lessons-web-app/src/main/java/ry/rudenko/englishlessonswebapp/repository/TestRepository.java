package ry.rudenko.englishlessonswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import ry.rudenko.englishlessonswebapp.model.entity.TestEntity;

public interface TestRepository extends JpaRepository<TestEntity, Long> {

  @Query("SELECT t FROM TestEntity t " +
      "WHERE :isFiltered = FALSE " +
      "OR LOWER(t.name) LIKE LOWER(CONCAT('%', :filter, '%'))" +
      "ORDER BY t.name")
  List<TestEntity> findAllByFilter(boolean isFiltered, String filter);
}
