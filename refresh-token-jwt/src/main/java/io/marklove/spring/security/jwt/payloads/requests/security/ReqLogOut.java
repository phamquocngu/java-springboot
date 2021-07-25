package io.marklove.spring.security.jwt.payloads.requests.security;

import io.marklove.spring.security.jwt.constants.ValidationCode;

import javax.validation.constraints.NotNull;

public class ReqLogOut {
  @NotNull(message = ValidationCode.VALIDATED_NULL)
  private Long userId;

  public Long getUserId() {
    return this.userId;
  }
}
