package ry.rudenko.englishlessonswebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ry.rudenko.englishlessonswebapp.model.dto.ThemeDto;

@Log4j2

@WithMockUser(username = "string", password = "string", roles = "USER")
@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ThemeControllerTest {

  @Autowired
  MockMvc mvc;
  @Autowired
  ObjectMapper objectMapper;

  @Test
  void fetchThemes() {

  }

  @Test
  void createTheme() throws Exception {
    String themeName = "Timestest";
    MvcResult result = mvc
        .perform(
            MockMvcRequestBuilders.post(
                "/api/v1/themes/{themeName}".replace("{themeName}", themeName)
            )
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    ThemeDto dto = objectMapper.readValue(result.getResponse().getContentAsString(),
        ThemeDto.class);
    assert (dto.getName().equals(themeName));
    log.info(result.getResponse().toString());
  }

  @Test
  void deleteTheme() {

  }
}


















