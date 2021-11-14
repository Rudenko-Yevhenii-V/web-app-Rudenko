package ry.rudenko.englishlessonsdictionary.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonsdictionary.access.CurrentUserProvider;

@RestController
@RequestMapping(path = "api/v1/client")
@ResponseBody
@AllArgsConstructor
public class GetAccessController {

  private final CurrentUserProvider currentUserProvider;

  @GetMapping(path = "access")
  public ResponseEntity<?> getAccess() {
    return ResponseEntity.ok(
        currentUserProvider.get().isEnabled() ? "Access granted" : "Forbidden!");
  }

}