package ry.rudenko.englishlessonswebapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;

@Service
@Slf4j
@AllArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService appUserDetailsService;

    public UserEntity login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return appUserDetailsService.retrieveFromCache(email);
    }
}
