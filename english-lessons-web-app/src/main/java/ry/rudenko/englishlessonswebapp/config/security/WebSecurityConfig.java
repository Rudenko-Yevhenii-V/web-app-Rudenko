package ry.rudenko.englishlessonswebapp.config.security;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ry.rudenko.englishlessonswebapp.Routes;
import ry.rudenko.englishlessonswebapp.auth.access.JwtTokenConfig;

@Configuration
@EnableWebSecurity()
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenConfig jwtTokenConfig;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
//        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
        .authorizeRequests()
        .antMatchers("/api/v1/auth/login", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",
            "/api/v1/auth/registration", "/api/v1/auth/current").permitAll()
        .antMatchers(HttpMethod.GET, Routes.API_ROOT + "/admin/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.PUT, Routes.API_ROOT + "/admin/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, Routes.API_ROOT + "/admin/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, Routes.API_ROOT + "/admin/**").hasRole("ADMIN")
        .anyRequest()
        .authenticated().and()
        .cors().configurationSource(corsConfigurationSource())
        .and()
        .formLogin().disable()
        .logout()
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .deleteCookies("JSESSIONID")
        .and()
        .apply(jwtTokenConfig);
  }

  private CorsConfigurationSource corsConfigurationSource() {
    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public WebMvcConfigurer corsConfig() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000", "http://localhost:8087");
      }
    };
  }
}
