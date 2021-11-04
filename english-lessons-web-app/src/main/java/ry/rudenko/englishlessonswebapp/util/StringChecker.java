package ry.rudenko.englishlessonswebapp.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import ry.rudenko.englishlessonswebapp.exception.BadRequestException;

@UtilityClass
public class StringChecker {

  public void checkOnEmpty(
      @NonNull String value,
      @NonNull String fieldName) {

    if (value.trim().isEmpty()) {
      throw new BadRequestException(String.format("Field \"%s\" can't be empty!", fieldName));
    }
  }
}