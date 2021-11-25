package ry.rudenko.englishlessonswebapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ry.rudenko.englishlessonswebapp.Routes;
import ry.rudenko.englishlessonswebapp.auth.bean.CreateTestRequest;
import ry.rudenko.englishlessonswebapp.model.dto.AckDto;
import ry.rudenko.englishlessonswebapp.model.dto.TestDto;
import ry.rudenko.englishlessonswebapp.service.TestService;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@Transactional
@RequestMapping(Routes.API_ROOT)
public class TestController {

  TestService testService;

  @GetMapping(Routes.FETCH_TESTS)
  public ResponseEntity<List<TestDto>> fetchTests(@RequestParam(defaultValue = "") String filter) {
    return ResponseEntity.ok(testService.createTestDtoList(filter));
  }

  @GetMapping(Routes.GET_TEST)
  public ResponseEntity<TestDto> getTest(@PathVariable Long testId) {
    return ResponseEntity.ok(testService.createTestDto(testId));
  }

  @PostMapping("/admin" + Routes.CREATE_OR_UPDATE_TEST)
  public ResponseEntity<TestDto> createOrUpdateTest(
      @RequestBody CreateTestRequest createTestRequest) {
    return ResponseEntity.ok(testService.createTestDtoServ(createTestRequest));
  }

  @DeleteMapping("/admin" + Routes.DELETE_TEST)
  public ResponseEntity<AckDto> deleteTest(
      @PathVariable Long testId) {
    return ResponseEntity.ok(testService.deleteTest(testId));
  }

  @PostMapping(Routes.COMPLETE_TEST)
  public ResponseEntity<AckDto> completeTest(
      @PathVariable Long testId,
      @PathVariable Long userId,
      @RequestParam Long answer) {
    return ResponseEntity.ok(testService.completeTest(testId, userId, answer));
  }
}