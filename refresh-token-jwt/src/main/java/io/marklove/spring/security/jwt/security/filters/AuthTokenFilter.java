package io.marklove.spring.security.jwt.security.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.marklove.spring.security.jwt.constants.MessageCode;
import io.marklove.spring.security.jwt.exception.JwtException;
import io.marklove.spring.security.jwt.payload.response.ErrorResponse;
import io.marklove.spring.security.jwt.security.jwt.JwtUtils;
import io.marklove.spring.security.jwt.security.services.impl.UserDetailsServiceImpl;
import io.marklove.spring.security.jwt.utils.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private UserDetailsServiceImpl userDetailsService;
  @Autowired
  private MessageService messageService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (JwtException e) {
      logger.error("Validate token error!");
      ErrorResponse errorResponse = new ErrorResponse(
              String.valueOf(HttpStatus.FORBIDDEN.value()),
              messageService.getMessage(e.getCode()),
              "");
      response.getWriter().write(convertObjectToJson(errorResponse));
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e.getMessage());
      ErrorResponse errorResponse = new ErrorResponse(
              String.valueOf(HttpStatus.FORBIDDEN.value()),
              messageService.getMessage(MessageCode.Error.code5009),
              "");
      response.getWriter().write(convertObjectToJson(errorResponse));
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }

    return null;
  }

  private String convertObjectToJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }
}
