package ry.rudenko.englishlessonswebapp.model.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
  @Column(unique = true)
  private String username;
  private String password;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<TodoEntity> todos;

  public UserEntity() {
  }

  public List<TodoEntity> getTodos() {
    return todos;
  }

  public void setTodos(List<TodoEntity> todos) {
    this.todos = todos;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
















