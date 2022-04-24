package io.marklove.carinsurance.entity;

import io.marklove.carinsurance.constant.PaymentStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private long amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "FK_payment_history_member"))
    private Member member;

    private PaymentStatus status;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

}
