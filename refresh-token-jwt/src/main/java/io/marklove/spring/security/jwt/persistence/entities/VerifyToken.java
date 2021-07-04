package io.marklove.spring.security.jwt.persistence.entities;

import io.marklove.spring.security.jwt.persistence.entities.common.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author ngupq
 */
@Table(name="verify_token")
@Entity
public class VerifyToken extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(name="verify_token")
    @NotNull
    private String verifyToken;
    @NotNull
    private LocalDateTime expired;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public VerifyToken(User user) {
        this.user = user;
        verifyToken = UUID.randomUUID().toString();
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public void setExpired(LocalDateTime expired) {
        this.expired = expired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
