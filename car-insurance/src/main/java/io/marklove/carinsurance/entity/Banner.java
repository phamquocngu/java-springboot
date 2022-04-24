package io.marklove.carinsurance.entity;

import io.marklove.carinsurance.constant.ShowStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Banner {

    @Id
    private int position;

    @Column(nullable = false)
    private ShowStatus status;

    @Column(nullable = false)
    private String imgUrl;

    private String connectionLink;

    public Banner(int position) {
        this.position = position;
    }
}