package ry.rudenko.englishlessonsdictionary.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ry.rudenko.englishlessonsdictionary.entity.UserEntity;
import ry.rudenko.englishlessonsdictionary.entity.WordEntity;

public interface WordEntityRepository extends JpaRepository<WordEntity, Long> {

   WordEntity findWordEntityById(Long id);
   WordEntity findWordEntityByWord(String word);
   boolean existsByWord( String word);
   List<WordEntity> findWordEntitiesByUserEntities(UserEntity userEntity);

}

