package ry.rudenko.englishlessonswebapp.model.dto;


import java.util.List;
import java.util.stream.Collectors;
import ry.rudenko.englishlessonswebapp.model.entity.UserEntity;

public class UserDto {
  private Long id;
  private String firstName;
  private List<TodoDto> todos;

public static  UserDto toDto(UserEntity userEntity){
  UserDto userDto = new UserDto();
  userDto.setId(userEntity.getId());
  userDto.setFirstName(userEntity.getFirstName());
  userDto.setTodos(userEntity.getTodos().stream().map(TodoDto::toDto).collect(Collectors.toList()));
  return userDto;

}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public List<TodoDto> getTodos() {
    return todos;
  }

  public void setTodos(List<TodoDto> todos) {
    this.todos = todos;
  }
}

















