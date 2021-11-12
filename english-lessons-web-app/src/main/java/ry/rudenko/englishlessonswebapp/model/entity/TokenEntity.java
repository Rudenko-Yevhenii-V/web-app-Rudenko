package ry.rudenko.englishlessonswebapp.model.entity;

import java.io.Serializable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "token")
public class TokenEntity   implements Serializable {

  private static final int EXPIRED_SECONDS = 60 * 60; // One hour expired

  @Id
  @Builder.Default
  String token = UUID.randomUUID().toString();

  @Builder.Default
  Instant expiredAt = Instant.now().plusSeconds(EXPIRED_SECONDS);

  @Builder.Default
  Instant createdAt = Instant.now();
}