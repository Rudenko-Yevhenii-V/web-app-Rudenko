package ry.rudenko.englishlessonswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ry.rudenko.englishlessonswebapp.model.entity.LessonEntity;

public interface UserLessonRepository extends JpaRepository<LessonEntity, Long> {

}



