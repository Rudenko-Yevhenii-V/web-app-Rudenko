package ry.rudenko.englishlessonsdictionary.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonsdictionary.repository.WordEntityRepository;
import javax.sql.DataSource;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class SqlInitService {

    private final DataSource dataSource;
    private final WordEntityRepository wordEntityRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        if(!wordEntityRepository.existsByWord("word")){
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false,
                    false, "UTF-8", new ClassPathResource("db/SQL.sql"));
            resourceDatabasePopulator.execute(dataSource);
        }

    }
}