package ry.rudenko.englishlessonsdictionary.access;


import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ry.rudenko.englishlessonsdictionary.entity.dto.CurrentUser;
import ry.rudenko.englishlessonsdictionary.provider.JwtSettingsProvider;

@Order(1)
@Component
@Slf4j
public class AccessFilter implements Filter {

  private final JwtSettingsProvider jwtSettingsProvider;
  private final CurrentUserProvider currentUserProvider;
  private final Gson gson = new Gson();
  @Value("${my.path.yml.urlname}")
  private String urlName;
  private HttpServletRequest httpServletRequest;

  public AccessFilter(
      JwtSettingsProvider jwtSettingsProvider,
      CurrentUserProvider currentUserProvider) {
    this.jwtSettingsProvider = jwtSettingsProvider;
    this.currentUserProvider = currentUserProvider;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    httpServletRequest = (HttpServletRequest) request;
    Cookie[] cookies = httpServletRequest.getCookies();
    CurrentUser currentUser = new CurrentUser();
    String authToken = null;
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(jwtSettingsProvider.getCookieAuthTokenName())) {
          authToken = cookie.getValue();
        }
      }
    }

    if (authToken != null && !authToken.isEmpty()) {
      currentUser = fetchRemoteUser(httpServletRequest);
    }

    currentUserProvider.set(currentUser);
    chain.doFilter(request, response);
  }

  private CurrentUser fetchRemoteUser(HttpServletRequest httpServletRequest) {
    try {

      URL url = new URL(urlName);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setConnectTimeout(2000);
      connection.setReadTimeout(1000);
      connection.setRequestProperty("Cookie", httpServletRequest.getHeader("Cookie"));
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestMethod("GET");

      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuilder content = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
      }
      in.close();

      connection.disconnect();

      return gson.fromJson(content.toString(), CurrentUser.class);

    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return new CurrentUser();
  }

  public CurrentUser getCurrentUser() {
    return fetchRemoteUser(httpServletRequest);
  }
}
