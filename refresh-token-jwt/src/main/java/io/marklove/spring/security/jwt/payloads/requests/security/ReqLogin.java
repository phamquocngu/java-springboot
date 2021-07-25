package io.marklove.spring.security.jwt.payloads.requests.security;

import io.marklove.spring.security.jwt.constants.ValidationCode;
import io.marklove.spring.security.jwt.utils.GetMessageService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ReqLogin {
	@NotBlank(message = ValidationCode.VALIDATED_BLANK)
	@Size(min = 4, max = 20, message = ValidationCode.VALIDATED_SIZE)
	private String username;

	@NotBlank(message = ValidationCode.VALIDATED_BLANK)
	@Size(min = 5, max = 50, message = ValidationCode.VALIDATED_SIZE)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
