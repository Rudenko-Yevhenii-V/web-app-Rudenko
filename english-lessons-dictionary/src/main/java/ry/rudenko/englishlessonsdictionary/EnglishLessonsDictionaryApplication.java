package ry.rudenko.englishlessonsdictionary;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@SpringBootApplication
public class EnglishLessonsDictionaryApplication {
	private final CurrentUserProvider currentUserProvider;

	public static void main(String[] args) {
		SpringApplication.run(EnglishLessonsDictionaryApplication.class, args);
	}
	@GetMapping(path = "access")
	public ResponseEntity<?> getAccess() {
		return ResponseEntity.ok(currentUserProvider.get().isEnabled() ? "Access granted" : "Forbidden!");
	}
	@GetMapping(path = "testqwer")
	public ResponseEntity<?> qwer() {
		return ResponseEntity.ok( "GetMapping testqwer");
	}
}
