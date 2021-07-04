package io.marklove.spring.security.jwt.payload.request;

import io.marklove.spring.security.jwt.constants.ValidationCode;

import javax.validation.constraints.NotBlank;

public class TokenRefreshRequest {
  @NotBlank(message = ValidationCode.VALIDATED_BLANK)
  private String refreshToken;

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
