package ry.rudenko.englishlessonswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ry.rudenko.englishlessonswebapp.model.entity.LessonEntity;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
  void deleteByIdAndThemeEntityId(Long lessonId, Long themeId);
}



