package io.marklove.springboot.jwt.persistences.entities;

import io.marklove.springboot.jwt.enums.ERole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author ngupq
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
}