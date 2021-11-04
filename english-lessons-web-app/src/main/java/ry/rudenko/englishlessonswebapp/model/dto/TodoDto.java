package ry.rudenko.englishlessonswebapp.model.dto;


import ry.rudenko.englishlessonswebapp.model.entity.TodoEntity;

public class TodoDto {

  private String title;
  private Boolean completed;
  private String description;

  public static TodoDto toDto(TodoEntity entity) {
    TodoDto model = new TodoDto();
    model.setCompleted(entity.getCompleted());
    model.setTitle(entity.getTitle());
    model.setDescription(entity.getDescription());
    return model;
  }

  public TodoDto() {
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
