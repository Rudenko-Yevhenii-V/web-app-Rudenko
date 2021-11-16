package ry.rudenko.englishlessonswebapp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.exception.BadRequestException;
import ry.rudenko.englishlessonswebapp.factory.ThemeDtoFactory;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.ThemeDto;
import ry.rudenko.englishlessonswebapp.model.entity.ThemeEntity;
import ry.rudenko.englishlessonswebapp.repository.ThemeRepository;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class ThemeService {
  ThemeRepository themeRepository;
  ThemeDtoFactory themeDtoFactory;

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














