package ry.rudenko.englishlessonswebapp.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ry.rudenko.englishlessonswebapp.repository.TodoRepo;
import org.mockito.Mockito;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

  @MockBean
  TodoRepo todoRepo;

  @Test
  void createTodo() {

//    Mockito.verify()
  }

  @Test
  void completeTodo() {
  }
}