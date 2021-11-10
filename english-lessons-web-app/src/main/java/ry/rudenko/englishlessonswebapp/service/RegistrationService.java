package ry.rudenko.englishlessonswebapp.service;


import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.auth.bean.RegistrationRequest;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserDetailsService appUserDetailsService;

    public UserEntity register(RegistrationRequest registrationRequest) {
        if(registrationRequest.getEmail().isEmpty()){
            throw new BadCredentialsException("Email is not corrected");
        }
        final UserEntity appUser = new UserEntity(registrationRequest);
        return appUserDetailsService.signUpUser(appUser);
    }
}
