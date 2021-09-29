package io.marklove.spring.security.jwt.payloads.requests.security;

import io.marklove.spring.security.jwt.constants.ValidationCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class TokenRefreshReq implements Serializable {
  @NotBlank(message = ValidationCode.VALIDATED_BLANK)
  private String refreshToken;
}
