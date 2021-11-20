package ry.rudenko.englishlessonswebapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ry.rudenko.englishlessonswebapp.auth.bean.RegistrationRequest;
import ry.rudenko.englishlessonswebapp.auth.bean.UserResponse;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;
import ry.rudenko.englishlessonswebapp.repository.UserEntityRepository;
import ry.rudenko.englishlessonswebapp.service.LoginService;
import ry.rudenko.englishlessonswebapp.service.RegistrationService;

import java.util.Optional;

@SpringBootTest
class WebAppApplicationTests {

	@Autowired
	private  RegistrationService registrationService;

	@Autowired
	private UserEntityRepository userEntityRepository;
	@Test
	void testRegisterUser() {
		var name = "name";
		var email = "email@dfg.com";
		var password = "1111";
		RegistrationRequest registrationRequest = new RegistrationRequest(name, email, password, true);
		UserEntity appUser = registrationService.register(registrationRequest);

		ResponseEntity<?> responseEntity = buildUserResponse(appUser);
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Optional<UserEntity> byEmail = userEntityRepository.findByEmail(email);
		UserEntity userEntity = byEmail.get();
		Assertions.assertNotNull(userEntity);
		Assertions.assertEquals(name, userEntity.getName());
		Assertions.assertEquals(email, userEntity.getEmail());
		Assertions.assertNotEquals(password, userEntity.getPassword());
		Assertions.assertNotNull(userEntity.getId());
		userEntityRepository.deleteAll();
	}
	private ResponseEntity<?> buildUserResponse(UserEntity appUser) {
		return ResponseEntity.ok(new UserResponse(appUser));
	}
}
