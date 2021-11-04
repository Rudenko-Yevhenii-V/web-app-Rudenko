package ry.rudenko.englishlessonswebapp.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.exception.BadRequestException;
import ry.rudenko.englishlessonswebapp.factory.ThemeDtoFactory;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.ThemeDto;
import ry.rudenko.englishlessonswebapp.model.entity.ThemeEntity;
import ry.rudenko.englishlessonswebapp.repository.ThemeRepository;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class ThemeService {
  ThemeRepository themeRepository;
  ThemeDtoFactory themeDtoFactory;

  public ThemeService(ThemeRepository themeRepository,
      ThemeDtoFactory themeDtoFactory) {
    this.themeRepository = themeRepository;
    this.themeDtoFactory = themeDtoFactory;
  }

  public List<ThemeDto> createThemeDtoList(String filter) {
    boolean isFiltered = !filter.trim().isEmpty();
    List<ThemeEntity> themes = themeRepository.findAllByFilter(isFiltered, filter);
    return themeDtoFactory.createThemeDtoList(themes);
  }

  public ThemeDto createThemeDto(String themeName) {
    if (themeRepository.existsByName(themeName)) {
      throw new BadRequestException(String.format("Theme  \"%s\" exist now!", themeName));
    }
    ThemeEntity theme = themeRepository.saveAndFlush(
        ThemeEntity.makeDefault(themeName)
    );
    return themeDtoFactory.createThemeDto(theme);
  }

  public AckDto deleteTheme(Long themeId) {
    if (themeRepository.existsById(themeId)) {
      themeRepository.deleteById(themeId);
    }
    return AckDto.makeDefault(true);
  }
}














