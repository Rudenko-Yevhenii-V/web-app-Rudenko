package ry.rudenko.englishlessonswebapp.service;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.repository.AppUserRepository;

@Log4j2
@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserEntity signUpUser(UserEntity appUser) {
        boolean userExists = userRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Email is already taken");
        }
        String encodedPassword = encodeString(appUser.getPassword());

        appUser.setPassword(encodedPassword);
        appUser.setEnabled(true);
        appUser = userRepository.save(appUser);

        if (!appUser.isEnabled()) {
            throw new IllegalStateException("The user is not enabled yet");
        }

        return appUser;
    }

    private String encodeString(String password) {
        return passwordEncoder.encode(password);
    }

    public UserEntity retrieveFromCache(String email) {
        return (UserEntity) new CachingUserDetailsService(this).loadUserByUsername(email);
    }
}
