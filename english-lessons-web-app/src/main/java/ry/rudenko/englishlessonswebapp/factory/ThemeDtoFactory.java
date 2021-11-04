package ry.rudenko.englishlessonswebapp.factory;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import ry.rudenko.englishlessonswebapp.model.dto.ThemeDto;
import ry.rudenko.englishlessonswebapp.model.entity.ThemeEntity;

@Component
public class ThemeDtoFactory {

  public ThemeDto createThemeDto(ThemeEntity entity) {
    return ThemeDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .build();
  }

  public List<ThemeDto> createThemeDtoList(List<ThemeEntity> entities) {
    return entities
        .stream()
        .map(this::createThemeDto)
        .collect(Collectors.toList());
  }
}