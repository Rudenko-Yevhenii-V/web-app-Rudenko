package ry.rudenko.englishlessonswebapp.controller;

import java.util.List;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ry.rudenko.englishlessonswebapp.exception.BadRequestException;
import ry.rudenko.englishlessonswebapp.factory.ThemeDtoFactory;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.ThemeDto;
import ry.rudenko.englishlessonswebapp.model.entity.ThemeEntity;
import ry.rudenko.englishlessonswebapp.repository.ThemeRepository;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
@Transactional
public class ThemeController {

  ThemeRepository themeRepository;
  ThemeDtoFactory themeDtoFactory;

  public static final String FETCH_THEME = "/themes";
  public static final String CREATE_THEME = "/themes/{themeName}";
  public static final String DELETE_THEME = "/themes/{themeId}";

  @GetMapping(FETCH_THEME)
  public ResponseEntity<List<ThemeDto>> fetchThemes(
      @RequestParam(defaultValue = "") String filter) {
    try {
      boolean isFiltered = !filter.trim().isEmpty();

      List<ThemeEntity> themes = themeRepository.findAllByFilter(isFiltered, filter);

      return ResponseEntity.ok(themeDtoFactory.createThemeDtoList(themes));
    } catch (Exception e) {
      throw new BadRequestException(String.format("An error has occurred! %s", e));
    }
  }

  @PostMapping(CREATE_THEME)
  public ResponseEntity<ThemeDto> createTheme(@PathVariable String themeName) {
    try {
      if (themeRepository.existsByName(themeName)) {
        throw new BadRequestException(String.format("Theme  \"%s\" exist now!", themeName));
      }

      ThemeEntity theme = themeRepository.saveAndFlush(
          ThemeEntity.makeDefault(themeName)
      );

      return ResponseEntity.ok(themeDtoFactory.createThemeDto(theme));
    } catch (Exception e) {
      throw new BadRequestException(String.format("An error has occurred! %s", e));
    }
  }

  @DeleteMapping(DELETE_THEME)
  public ResponseEntity<AckDto> deleteTheme(@PathVariable Long themeId) {
    try {
      if (themeRepository.existsById(themeId)) {
        themeRepository.deleteById(themeId);
      }
      return ResponseEntity.ok(AckDto.makeDefault(true));
    } catch (Exception e) {
      throw new BadRequestException(String.format("An error has occurred! %s", e));
    }
  }
}

