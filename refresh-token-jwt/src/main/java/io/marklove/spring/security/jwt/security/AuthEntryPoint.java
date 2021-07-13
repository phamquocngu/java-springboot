package io.marklove.spring.security.jwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.marklove.spring.security.jwt.constants.AuthConstants;
import io.marklove.spring.security.jwt.payloads.responses.security.UnAuthResponse;
import io.marklove.spring.security.jwt.utils.GetMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPoint.class);

  @Autowired
  private GetMessageService messageService;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException {
    logger.error("Unauthorized error: {}", authException.getMessage());

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    UnAuthResponse body;

    final String expired = (String) request.getAttribute("expired");
    if(expired != null) {
      body = new UnAuthResponse(HttpStatus.UNAUTHORIZED.value(),
              AuthConstants.Error.EXPIRED_JWT,
              messageService.getMessage(AuthConstants.Error.EXPIRED_JWT));
    } else {
      body = new UnAuthResponse(HttpStatus.UNAUTHORIZED.value(),
              AuthConstants.Error.UNAUTHORIZED,
              messageService.getMessage(AuthConstants.Error.UNAUTHORIZED));
    }

    final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build();

    mapper.writeValue(response.getOutputStream(), body);
  }
}
