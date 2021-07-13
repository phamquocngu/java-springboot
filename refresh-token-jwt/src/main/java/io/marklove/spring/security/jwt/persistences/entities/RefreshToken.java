package io.marklove.spring.security.jwt.persistences.entities;

import java.time.Instant;

import javax.persistence.*;

/**
 * @author ngupq
 */
@Table(name = "refresh_token")
@Entity
public class RefreshToken extends BaseEntity {
  private static final long serialVersionUID = 1L;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
  @Column(nullable = false, unique = true)
  private String token;
  @Column(nullable = false)
  private Instant expiryDate;

  public RefreshToken() {
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Instant getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Instant expiryDate) {
    this.expiryDate = expiryDate;
  }

}
