package ry.rudenko.englishlessonswebapp.repository;


import java.util.List;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ry.rudenko.englishlessonswebapp.model.entity.ThemeEntity;

public interface ThemeRepository extends JpaRepository<ThemeEntity, Long> {
  Boolean existsByName(@NonNull String name);

  @Query("SELECT s FROM ThemeEntity s " +
      "WHERE :isFiltered = FALSE " +
      "OR LOWER(s.name) LIKE LOWER(CONCAT('%',:filter, '%')) " +
      "ORDER BY s.name DESC")
  List<ThemeEntity> findAllByFilter(boolean isFiltered, String filter);

}
