package io.marklove.springboot.jwt.exceptions;

public class TokenRefreshException extends BaseRuntimeException {
  private static final long serialVersionUID = 1L;

  public TokenRefreshException(String code) {
    super(code);
  }
}
