package io.marklove.springboot.jwt.persistences.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author ngupq
 */
@Table(name="verify_token")
@Entity
@Getter
@Setter
public class VerifyToken extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(name="verify_token")
    @NotNull
    private String verifyToken;

    @NotNull
    private LocalDateTime expired;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public VerifyToken(User user) {
        this.user = user;
        verifyToken = UUID.randomUUID().toString();
    }
}
