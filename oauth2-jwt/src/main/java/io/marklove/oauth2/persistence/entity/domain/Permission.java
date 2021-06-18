package io.marklove.oauth2.persistence.entity.domain;

import io.marklove.oauth2.persistence.entity.common.BaseEntity;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@EqualsAndHashCode(callSuper = false)
public class Permission extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
