package ry.rudenko.englishlessonswebapp.model.entity;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "todo")
public class TodoEntity   implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
   Long id;
   String title;
   Boolean completed;
   String description;

  @ManyToOne
  @JoinColumn(name = "user_id")
   UserEntity user;

    public static TodoEntity makeDefault(
            String title,
            Boolean completed,
            String description,
            UserEntity user) {
        return builder()
                .title(title)
                .completed(completed)
                .description(description)
                .user(user)
                .build();
    }

}