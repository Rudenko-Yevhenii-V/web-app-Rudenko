package ry.rudenko.englishlessonswebapp.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.auth.access.JwtTokenProvider;
import ry.rudenko.englishlessonswebapp.auth.bean.LoginRequest;
import ry.rudenko.englishlessonswebapp.auth.bean.RegistrationRequest;
import ry.rudenko.englishlessonswebapp.enums.UserRole;
import ry.rudenko.englishlessonswebapp.exception.NotFoundException;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.repository.UserEntityRepository;
import ry.rudenko.englishlessonswebapp.util.StringChecker;

@RequiredArgsConstructor
@ExtensionMethod(StringChecker.class)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class AuthService {

  UserEntityRepository userRepository;
  RegistrationService registrationService;
  LoginService loginService;
  JwtTokenProvider jwtTokenProvider;


  public UserEntity registration(RegistrationRequest registrationRequest, HttpServletResponse response) {
    UserEntity appUser = registrationService.register(registrationRequest);
    appUser.setRole(UserRole.USER);
    setAuthToken(appUser, response);
    setRefreshToken(appUser, response);
    return appUser;
  }

  private String setAuthToken(UserEntity appUser, HttpServletResponse httpServletResponse) {
    String token = jwtTokenProvider.createAuthToken(appUser.getEmail(), appUser.getRole().name());
    Cookie cookie = new Cookie(jwtTokenProvider.getAuthCookieName(), token);
    cookie.setPath(jwtTokenProvider.getCookiePath());
    cookie.setHttpOnly(true);
    cookie.setMaxAge(jwtTokenProvider.getAuthExpirationCookie());
    httpServletResponse.addCookie(cookie);
    return token;
  }

  private void setRefreshToken(UserEntity appUser, HttpServletResponse httpServletResponse) {
    String token = jwtTokenProvider.createRefreshToken(appUser.getEmail(), appUser.getRole().name());
    Cookie cookie = new Cookie(jwtTokenProvider.getRefreshCookieName(), token);
    cookie.setPath(jwtTokenProvider.getCookiePath());
    cookie.setHttpOnly(true);
    cookie.setMaxAge(jwtTokenProvider.getRefreshExpirationCookie());
    httpServletResponse.addCookie(cookie);
  }


  public Object loginUser(LoginRequest loginRequest, HttpServletResponse response) {
    UserEntity appUser = loginService.login(loginRequest.getEmail(), loginRequest.getPassword());
    final String token = setAuthToken(appUser, response);
    setRefreshToken(appUser, response);
    return token;
  }

  public UserEntity current() {
    UserEntity principal = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userRepository.findByEmail(principal.getEmail()).orElseThrow(() ->
        new NotFoundException("User not found"));
  }

  public void logout(HttpServletResponse httpServletResponse) {
    clearAuthAndRefreshTokens(httpServletResponse);
    SecurityContextHolder.clearContext();
  }
  public void clearAuthAndRefreshTokens(HttpServletResponse httpServletResponse) {
    Cookie authCookie = new Cookie(jwtTokenProvider.getAuthCookieName(), "-");
    authCookie.setPath(jwtTokenProvider.getCookiePath());

    Cookie refreshCookie = new Cookie(jwtTokenProvider.getRefreshCookieName(), "-");
    refreshCookie.setPath(jwtTokenProvider.getCookiePath());

    httpServletResponse.addCookie(authCookie);
    httpServletResponse.addCookie(refreshCookie);
  }
}










