package io.marklove.spring.security.jwt.payloads.responses.security;

import io.marklove.spring.security.jwt.constants.AuthConstants;

import java.time.LocalDateTime;

public class RefreshTokenResponse {
  private String accessToken;
  private String refreshToken;
  private String tokenType = AuthConstants.BEARER;
  private LocalDateTime dateTime;

  public RefreshTokenResponse(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.dateTime = LocalDateTime.now();
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String token) {
    this.accessToken = token;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

}
