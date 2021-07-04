package io.marklove.spring.security.jwt.payload.request;

import io.marklove.spring.security.jwt.constants.ValidationCode;

import javax.validation.constraints.NotBlank;

public class LogOutRequest {
  @NotBlank(message = ValidationCode.VALIDATED_BLANK)
  private Long userId;

  public Long getUserId() {
    return this.userId;
  }
}
