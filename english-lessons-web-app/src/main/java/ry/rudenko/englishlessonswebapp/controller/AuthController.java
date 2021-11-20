package ry.rudenko.englishlessonswebapp.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonswebapp.Routes;
import ry.rudenko.englishlessonswebapp.auth.bean.ErrorResponse;
import ry.rudenko.englishlessonswebapp.auth.bean.LoginRequest;
import ry.rudenko.englishlessonswebapp.auth.bean.RegistrationRequest;
import ry.rudenko.englishlessonswebapp.auth.bean.UserResponse;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.service.AuthService;

@RestController
@ResponseBody
@RequestMapping(path = Routes.API_ROOT + "/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

AuthService authService;
    @PostMapping(Routes.USER_REG)
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationRequest registrationRequest, HttpServletResponse response) {
        try {

            return buildUserResponse(authService.registration(registrationRequest, response));
        } catch (Exception e) {
            authService.clearAuthAndRefreshTokens(response);
            return buildErrorResponse(e.getLocalizedMessage());
        }
    }

    @PostMapping(Routes.USER_LOGIN)
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            return ResponseEntity.ok(authService.loginUser(loginRequest, response));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            authService.clearAuthAndRefreshTokens(response);
            return buildErrorResponse(e.getLocalizedMessage());
        }
    }

    @GetMapping(Routes.USER_CURRENT)
    public ResponseEntity<?> current() {
        try {
            return buildUserResponse(authService.current());
        } catch (NullPointerException e) {
            log.error(e.getLocalizedMessage());
        }
        return buildUserResponse(new UserEntity());
    }

    @GetMapping(Routes.USER_LOGOUT)
    public ResponseEntity<?> logout(HttpServletResponse httpServletResponse) {
        authService.logout(httpServletResponse);
        return buildUserResponse(new UserEntity());
    }

    private ResponseEntity<?> buildUserResponse(UserEntity appUser) {
        return ResponseEntity.ok(new UserResponse(appUser));
    }

    private ResponseEntity<?> buildErrorResponse(String message) {
        return ResponseEntity.ok(new ErrorResponse(message));
    }

}