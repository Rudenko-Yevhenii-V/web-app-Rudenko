package ry.rudenko.englishlessonswebapp;

public final class Routes {

  private Routes() {
    throw new AssertionError("non-instantiable class");
  }

  public static final String API_ROOT = "/api/v1/auth";

  public static final String FETCH_LESSONS = "/themes/{themeId}/lessons";
  public static final String CREATE_LESSONS = "/themes/{themeId}/lessons/{lessonsText}";
  public static final String DELETE_LESSON = "/themes/{themeId}/lessons/{lessonId}";
  public static final String ADD_TO_USER_LESSON = "/themes/{themeId}/lessons/{lessonId}/{user_id}";

  public static final String FETCH_TESTS = "/tests";
  public static final String GET_TEST = "/tests/{testId}";
  public static final String CREATE_OR_UPDATE_TEST = "/tests";
  public static final String DELETE_TEST = "/tests/{testId}";
  public static final String COMPLETE_TEST = "/tests/{testId}/users/{userId}/compete";

  public static final String FETCH_THEME = "/themes";
  public static final String CREATE_THEME = "/themes/{themeName}";
  public static final String DELETE_THEME = "/themes/{themeId}";

  public static final String FETCH_USERS = "users/all";
  public static final String FETCH_USERS_BY_THEME = "/{lessonId}/users";
  public static final String UPDATE_USER = "/users/update";
  public static final String UPDATE_USER_ROLE = "/users/update/role";
  public static final String DELETE_USER = "/users/{userId}";

  public static final String USER_REG = "/registration";
  public static final String USER_LOGIN = "/login";
  public static final String USER_CURRENT = "/current";
  public static final String USER_LOGOUT= "/logout";
}
