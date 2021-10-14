package ry.rudenko.englishlessonswebapp.model.dto;


import ry.rudenko.englishlessonswebapp.model.entity.TodoEntity;

public class TodoDto {
    private Long id;
    private String title;
    private Boolean completed;

    public static TodoDto toDto(TodoEntity entity) {
      TodoDto model = new TodoDto();
      model.setId(entity.getId());
      model.setCompleted(entity.getCompleted());
      model.setTitle(entity.getTitle());
      return model;
    }

    public TodoDto() {
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
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
  }
