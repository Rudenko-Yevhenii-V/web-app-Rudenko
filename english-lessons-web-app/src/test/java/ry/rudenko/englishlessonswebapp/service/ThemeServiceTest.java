package ry.rudenko.englishlessonswebapp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ry.rudenko.englishlessonswebapp.factory.ThemeDtoFactory;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.ThemeDto;
import ry.rudenko.englishlessonswebapp.model.entity.ThemeEntity;
import ry.rudenko.englishlessonswebapp.repository.ThemeRepository;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ThemeServiceTest {

  @Autowired
  ThemeRepository themeRepository;
  @Autowired
  ThemeDtoFactory themeDtoFactory;
  @Autowired
  ThemeService themeService;
  ThemeEntity theme;

  @BeforeEach
  void init() {
    theme = themeRepository.saveAndFlush(ThemeEntity.makeDefault("Test theme!"));
  }

  @Test
  void createThemeDtoList() {
    final Optional<ThemeEntity> byId = themeRepository.findById(theme.getId());
    assert (Objects.equals(byId.get().getName(), "Test theme!"));
    List<ThemeEntity> themes = themeRepository.findAllByFilter(false, "Test");
    Assertions.assertTrue(themes.size() > 0);
    final List<ThemeDto> themeDtoList = themeDtoFactory.createThemeDtoList(themes);
    Assertions.assertEquals(themeDtoList.get(0).getClass(), ThemeDto.class);
  }

  @Test
  void createThemeDto() {
    final ThemeDto lorem = themeService.createThemeDto("Lorem");
    assertNotNull(lorem);
  }

  @Test
  void deleteTheme() {
    final AckDto ackDto = themeService.deleteTheme(theme.getId());
    Assertions.assertTrue(ackDto.getAnswer());
    final Optional<ThemeEntity> byId = themeRepository.findById(theme.getId());
    Assertions.assertTrue(byId.isEmpty());

  }
}