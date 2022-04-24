package io.marklove.carinsurance.entity;


import io.marklove.carinsurance.constant.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = Role.Constant.ADMIN)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Admin extends User {

    private String encodePwd;
}