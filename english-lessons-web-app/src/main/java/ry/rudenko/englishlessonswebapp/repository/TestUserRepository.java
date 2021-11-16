package ry.rudenko.englishlessonswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ry.rudenko.englishlessonswebapp.model.entity.TestUserEntity;

public interface TestUserRepository extends JpaRepository<TestUserEntity, Long> {

}
