package ry.rudenko.englishlessonswebapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.exception.UserAlreadyExistException;
import ry.rudenko.englishlessonswebapp.exception.UserNotFoundException;
import ry.rudenko.englishlessonswebapp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity registration(@RequestBody UserEntity user) {
    try {
      userService.registration(user);
      return ResponseEntity.ok("User saved!");
    } catch (UserAlreadyExistException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
  } catch (Exception e) {
    return ResponseEntity.badRequest().body("An error has occurred");
  }
  }

//  @GetMapping
//  public ResponseEntity getUser() {
//    try {
//      return ResponseEntity.ok("Server is running !");
//    } catch (Exception e) {
//      return ResponseEntity.badRequest().body("An error has occurred");
//    }
//  }


//  TODO (/${id})
//@GetMapping()
//public ResponseEntity getOneUser(@RequestParam Long id) {
//  try {
//    return ResponseEntity.ok(userService.getOneUser(id));
//  } catch (UserNotFoundException e) {
//    return ResponseEntity.badRequest().body(e.getMessage());
//  }catch (Exception e) {
//    return ResponseEntity.badRequest().body("An error has occurred");
//  }
//}

@GetMapping("/{id}")
public ResponseEntity getUser(@PathVariable Long id) {
  try {
    System.out.println("id = " + id);
    return ResponseEntity.ok(userService.getOneUser(id));
  } catch (UserNotFoundException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }catch (Exception e) {
    return ResponseEntity.badRequest().body("An error has occurred");
  }
}




  @DeleteMapping("/{id}")
  public ResponseEntity deleteUser(@PathVariable Long id){
    try {
      return ResponseEntity.ok("Deleted user id = " + userService.delete(id));
    }catch (Exception e) {
      return ResponseEntity.badRequest().body("An error has occurred");
    }
  }

}




















