package io.marklove.spring.security.jwt.exceptions;

public class TokenRefreshException extends BaseRuntimeException {
  private static final long serialVersionUID = 1L;

  public TokenRefreshException(String code) {
    super(code);
  }
}
