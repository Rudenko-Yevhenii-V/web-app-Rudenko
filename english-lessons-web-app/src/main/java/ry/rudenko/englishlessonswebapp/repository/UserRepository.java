package ry.rudenko.englishlessonswebapp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByFirstName(String firstName);

  @Query("SELECT u FROM UserEntity u " +
      "WHERE :isFiltered = FALSE " +
      "OR (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :filter, '%')) " +
      "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :filter, '%'))) " +
      "ORDER BY u.lastName, u.firstName")
  List<UserEntity> findAllByFilter(boolean isFiltered, String filter);

  @Query("SELECT u FROM UserEntity u "
      + " where u.lessons.id =:lessonEntityId and (:isFiltered = FALSE " +
      "OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :filter, '%'))" +
      "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :filter, '%'))" +
      ") " +
      "ORDER BY u.lastName, u.firstName")
  List<UserEntity> findAllByFilterAndLessonEntityId(boolean isFiltered, String filter, Long lessonEntityId);

  @Query("SELECT u FROM UserEntity u WHERE u.id =:userId")
  Optional<UserEntity> findById(Long userId);

  Optional<UserEntity> findTopByLoginAndPassword(@NonNull String login, @NonNull String password);
}

