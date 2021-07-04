package io.marklove.spring.security.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends BaseRuntimeException {
  private static final long serialVersionUID = 1L;
  private String token;

  public TokenRefreshException(String token, String code) {
    super(code);
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
