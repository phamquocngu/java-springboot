package io.marklove.spring.security.jwt.persistence.entities;

import io.marklove.spring.security.jwt.persistence.entities.common.BaseEntity;
import io.marklove.spring.security.jwt.persistence.enums.ERole;

import javax.persistence.*;

/**
 * @author ngupq
 */
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	public Role() {}

	public Role(ERole name) {
		this.name = name;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}