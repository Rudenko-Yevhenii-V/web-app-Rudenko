package ry.rudenko.englishlessonswebapp.controller;

import java.util.List;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonswebapp.Routes;
import ry.rudenko.englishlessonswebapp.exception.BadRequestException;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.ThemeDto;
import ry.rudenko.englishlessonswebapp.service.ThemeService;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@Transactional
@RequestMapping(Routes.API_ROOT)
public class ThemeController {

  ThemeService themeService;

  @GetMapping(Routes.FETCH_THEME)
  public ResponseEntity<List<ThemeDto>> fetchThemes(
      @RequestParam(defaultValue = "") String filter) {
    try {
      return ResponseEntity.ok(themeService.createThemeDtoList(filter));
    } catch (Exception e) {
      throw new BadRequestException(String.format("An error has occurred! %s", e));
    }
  }

  @PostMapping("/admin" + Routes.CREATE_THEME)
  public ResponseEntity<ThemeDto> createTheme(@PathVariable String themeName) {
    try {
      return ResponseEntity.ok(themeService.createThemeDto(themeName));
    } catch (Exception e) {
      throw new BadRequestException(String.format("An error has occurred! %s", e));
    }
  }

  @DeleteMapping("/admin" + Routes.DELETE_THEME)
  public ResponseEntity<AckDto> deleteTheme(@PathVariable Long themeId) {
    try {
      return ResponseEntity.ok(themeService.deleteTheme(themeId));
    } catch (Exception e) {
      throw new BadRequestException(String.format("An error has occurred! %s", e));
    }
  }
}

