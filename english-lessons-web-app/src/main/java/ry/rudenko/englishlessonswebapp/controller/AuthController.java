package ry.rudenko.englishlessonswebapp.controller;

import ry.rudenko.englishlessonswebapp.Routes;
import ry.rudenko.englishlessonswebapp.auth.access.JwtTokenProvider;
import ry.rudenko.englishlessonswebapp.auth.bean.ErrorResponse;
import ry.rudenko.englishlessonswebapp.auth.bean.LoginRequest;
import ry.rudenko.englishlessonswebapp.auth.bean.RegistrationRequest;
import ry.rudenko.englishlessonswebapp.auth.bean.UserResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.service.LoginService;
import ry.rudenko.englishlessonswebapp.service.RegistrationService;

@RestController
@ResponseBody
@RequestMapping(path = Routes.API_ROOT +"/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(Routes.USER_REG)
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest registrationRequest, HttpServletResponse response) {
        try {
            UserEntity appUser = registrationService.register(registrationRequest);
            setAuthToken(appUser, response);
            setRefreshToken(appUser, response);
            return buildUserResponse(appUser);
        } catch (Exception e) {
            clearAuthAndRefreshTokens(response);
            return buildErrorResponse(e.getLocalizedMessage());
        }
    }

    @PostMapping(Routes.USER_LOGIN)
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {

            UserEntity appUser = loginService.login(loginRequest.getEmail(), loginRequest.getPassword());
            final String token = setAuthToken(appUser, response);
            setRefreshToken(appUser, response);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            clearAuthAndRefreshTokens(response);
            return buildErrorResponse(e.getLocalizedMessage());
        }
    }

    @GetMapping(Routes.USER_CURRENT)
    public ResponseEntity<?> current() {
        try {
            UserEntity appUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return buildUserResponse(appUser);
        } catch (NullPointerException e) {
            log.error(e.getLocalizedMessage());
        }
        return buildUserResponse(new UserEntity());
    }

    @GetMapping(Routes.USER_LOGOUT)
    public ResponseEntity<?> logout(HttpServletResponse httpServletResponse) {
        clearAuthAndRefreshTokens(httpServletResponse);
        SecurityContextHolder.clearContext();
        return buildUserResponse(new UserEntity());
    }

    private void clearAuthAndRefreshTokens(HttpServletResponse httpServletResponse) {
        Cookie authCookie = new Cookie(jwtTokenProvider.getAuthCookieName(), "-");
        authCookie.setPath(jwtTokenProvider.getCookiePath());

        Cookie refreshCookie = new Cookie(jwtTokenProvider.getRefreshCookieName(), "-");
        refreshCookie.setPath(jwtTokenProvider.getCookiePath());

        httpServletResponse.addCookie(authCookie);
        httpServletResponse.addCookie(refreshCookie);
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

    private ResponseEntity<?> buildUserResponse(UserEntity appUser) {
        return ResponseEntity.ok(new UserResponse(appUser));
    }

    private ResponseEntity<?> buildErrorResponse(String message) {
        return ResponseEntity.ok(new ErrorResponse(message));
    }

}