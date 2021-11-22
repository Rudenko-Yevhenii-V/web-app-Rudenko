package ry.rudenko.englishlessonsdictionary.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonsdictionary.access.AccessFilter;
import ry.rudenko.englishlessonsdictionary.access.CurrentUserProvider;
import ry.rudenko.englishlessonsdictionary.entity.dto.CurrentUser;

@RestController
@RequestMapping(path = "api/v1/client")
@ResponseBody
@AllArgsConstructor
public class GetAccessController {

  private final CurrentUserProvider currentUserProvider;
  private final AccessFilter accessFilter;


  @GetMapping(path = "access_user")
  public ResponseEntity<?> getAccessUser() {
    CurrentUser currentUser = accessFilter.getCurrentUser();
    if (currentUser.getEmail() == null) {
      currentUser = new CurrentUser();
      currentUser.setName("guest");
    }
    String user = currentUser.getName();
    System.out.println("user = " + user);
    return ResponseEntity.ok(
        currentUserProvider.get().isEnabled() ? "Access granted. Hello " + user
            : "Forbidden! Hello " + user);
  }

}
