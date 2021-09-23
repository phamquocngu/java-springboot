package io.marklove.spring.security.jwt.persistences.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

import javax.persistence.*;

/**
 * @author ngupq
 */
@Table(name = "refresh_token")
@Entity
@Getter
@Setter
public class RefreshToken extends BaseEntity {
  private static final long serialVersionUID = 1L;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
  @Column(nullable = false, unique = true)
  private String token;
  @Column(nullable = false)
  private Instant expiryDate;
}
