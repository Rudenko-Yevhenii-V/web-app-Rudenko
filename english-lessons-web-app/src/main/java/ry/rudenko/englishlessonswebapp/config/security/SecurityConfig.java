package ry.rudenko.englishlessonswebapp.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
//@EnableConfigurationProperties(FileSharingSecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

//    private final FileSharingSecurityProperties securityProperties;
//
//    private final UserService userService;
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final ObjectMapper objectMapper;

//    public SecurityConfig(
//            FileSharingSecurityProperties securityProperties,
//            UserService userService,
//            PasswordEncoder passwordEncoder,
//            ObjectMapper objectMapper
//    ) {
//        this.securityProperties = securityProperties;
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//        this.objectMapper = objectMapper;
//    }
//
//    @PostConstruct
//    public void init() {
//        setupDefaultAdmins();
//    }
//
//    private void setupDefaultAdmins() {
//        List<SaveUserRequest> requests = securityProperties.getAdmins().entrySet().stream()
//                .map(entry -> new SaveUserRequest(
//                        entry.getValue().getEmail(),
//                        new String(entry.getValue().getPassword()),
//                        entry.getKey()))
//                .peek(admin -> log.info("Default admin found: {} <{}>", admin.nickname(), admin.email()))
//                .collect(Collectors.toList());
//        userService.mergeAdmins(requests);
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
//    }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        // open static resources
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        // open swagger-ui
        .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
            , "/users*", "/**").permitAll()
        // allow user registration and refresh, ignore authorization filters on login
//                .antMatchers(HttpMethod.POST, Routes.USERS, Routes.TOKEN + "/refresh").permitAll()
        // admin can register new admins
//                .antMatchers(HttpMethod.POST, Routes.USERS + "/admins").hasRole("ADMIN")
        // regular users can view basic user info for other users
//                .antMatchers(HttpMethod.GET,Routes.USERS + "/{id:\\d+}").authenticated()
        // admin can manage users by id
//                .antMatchers(Routes.USERS + "/{id:\\d+}/**").hasRole("ADMIN")
        // admin can use Actuator endpoints
        .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
        // by default, require authentication
        .anyRequest().authenticated()
        .and()
        // auth filter
//                .addFilter(jwtAuthenticationFilter())
        // jwt-verification filter
//                .addFilter(jwtAuthorizationFilter())
        // for unauthorized requests return 401
        .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        .and()
        // allow cross-origin requests for all endpoints
        .cors().configurationSource(corsConfigurationSource())
        .and()
        .csrf().disable()
        // this disables session creation on Spring Security
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }



  private CorsConfigurationSource corsConfigurationSource() {
    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}