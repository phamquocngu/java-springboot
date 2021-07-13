package io.marklove.spring.security.jwt.payloads.responses;

import java.time.LocalDateTime;

public class ErrorResponse {
  private String code;
  private String message;
  private String description;
  private LocalDateTime dateTime;

  public ErrorResponse(String code, String message, String description) {
    this.code = code;
    this.dateTime = LocalDateTime.now();
    this.message = message;
    this.description = description;
  }

  public ErrorResponse(String code, String message, String description, LocalDateTime dateTime) {
    this.code = code;
    this.dateTime = dateTime;
    this.message = message;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getDescription() {
    return description;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }
}