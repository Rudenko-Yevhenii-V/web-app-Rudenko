package ry.rudenko.englishlessonswebapp.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ry.rudenko.englishlessonswebapp.auth.bean.RegistrationRequest;
import ry.rudenko.englishlessonswebapp.enums.UserRole;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users_entity")
public class UserEntity implements UserDetails, Serializable {

//todo UUID need add!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false, updatable = false)
  Long id;

  @Column(unique = true)
  String login;

  @Column(name = "password")
  String password;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  List<TodoEntity> todos;

  @Column(name = "name")
  String name;

  String lastName;

  String middleName;

  @Column(name = "email", unique = true)
   String email;

  Instant birthday;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  UserRole role = UserRole.USER;


  @ManyToOne
  @JoinColumn(name = "lesson_id")
  private LessonEntity lessons;

  @Column(name = "locked")
  private boolean locked;

  @Column(name = "enabled")
  private boolean enabled;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.name());
    return Collections.singletonList(authority);
  }
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }


  public static UserEntity makeDefault(
      String firstName,
      String middleName,
      String lastName,
      Instant birthday,
      UserRole role) {
    return builder()
        .name(firstName)
        .middleName(middleName)
        .lastName(lastName)
        .birthday(birthday)
        .role(role)
        .build();
  }
  public UserEntity(RegistrationRequest registrationRequest) {
    this.name = registrationRequest.getName();
    this.email = registrationRequest.getEmail();
    this.password = registrationRequest.getPassword();
  }
}














