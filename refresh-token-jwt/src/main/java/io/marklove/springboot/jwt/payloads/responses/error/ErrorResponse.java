package io.marklove.springboot.jwt.payloads.responses.error;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
  private String code;
  private String message;
  private Object detail;
  private LocalDateTime dateTime;

  public ErrorResponse(String code, String message, Object detail) {
    this.code = code;
    this.dateTime = LocalDateTime.now();
    this.message = message;
    this.detail = detail;
  }

  public ErrorResponse(String code, String message, Object detail, LocalDateTime dateTime) {
    this.code = code;
    this.dateTime = dateTime;
    this.message = message;
    this.detail = detail;
  }
}