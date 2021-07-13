package io.marklove.spring.security.jwt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends BaseRuntimeException {
  private static final long serialVersionUID = 1L;

  public TokenRefreshException(String code) {
    super(code);
  }
}
