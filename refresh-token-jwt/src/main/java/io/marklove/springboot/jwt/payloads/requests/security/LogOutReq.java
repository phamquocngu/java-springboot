package io.marklove.springboot.jwt.payloads.requests.security;

import io.marklove.springboot.jwt.constants.ValidationCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ngupq
 */
@Getter
@Setter
public class LogOutReq implements Serializable {
  @NotNull(message = ValidationCode.VALIDATED_NULL)
  private Long userId;
}
