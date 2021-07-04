package io.marklove.spring.security.jwt.advice;

import io.marklove.spring.security.jwt.constants.MessageCode;
import io.marklove.spring.security.jwt.exception.TokenRefreshException;
import io.marklove.spring.security.jwt.payload.response.ErrorResponse;
import io.marklove.spring.security.jwt.utils.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class TokenControllerAdvice {

  @Autowired
  private MessageService messageService;

  @ExceptionHandler(value = TokenRefreshException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorResponse handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
    return new ErrorResponse(
        String.valueOf(HttpStatus.FORBIDDEN.value()),
        String.format(messageService.getMessage(MessageCode.Format.FAILED_TOKEN), ex.getToken(),
                messageService.getMessage(ex.getCode())),
        request.getDescription(false));
  }
}
