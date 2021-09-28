package io.marklove.spring.security.jwt.payloads.requests.security;

import io.marklove.spring.security.jwt.constants.ValidationCode;
import io.marklove.spring.security.jwt.enums.EGrantType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author ngupq
 */
@Getter
@Setter
public class LoginReq implements Serializable {
	@NotBlank(message = ValidationCode.VALIDATED_BLANK)
	@Size(min = 4, max = 20, message = ValidationCode.VALIDATED_SIZE)
	private String username;

	@NotBlank(message = ValidationCode.VALIDATED_BLANK)
	@Size(min = 5, max = 50, message = ValidationCode.VALIDATED_SIZE)
	private String password;

	private EGrantType grantType;

	private String refreshToken;
}
