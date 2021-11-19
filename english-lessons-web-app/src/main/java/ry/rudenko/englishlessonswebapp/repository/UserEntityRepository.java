package ry.rudenko.englishlessonswebapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);

  @Query("SELECT u FROM UserEntity u " +
      "WHERE :isFiltered = FALSE " +
      "OR (LOWER(u.name) LIKE LOWER(CONCAT('%', :filter, '%')) " +
      "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :filter, '%'))) " +
      "ORDER BY u.lastName, u.name")
  List<UserEntity> findAllByFilter(boolean isFiltered, String filter);

  @Query("SELECT u FROM UserEntity u "
      + " where u.lessons.id =:lessonEntityId and (:isFiltered = FALSE " +
      "OR LOWER(u.name) LIKE LOWER(CONCAT('%', :filter, '%'))" +
      "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :filter, '%'))" +
      ") " +
      "ORDER BY u.lastName, u.name")
  List<UserEntity> findAllByFilterAndLessonEntityId(boolean isFiltered, String filter, Long lessonEntityId);

  @Query("SELECT u FROM UserEntity u WHERE u.id =:userId")
  @NonNull
  Optional<UserEntity> findById(@NonNull Long userId);
}

