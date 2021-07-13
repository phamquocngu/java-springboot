package io.marklove.spring.security.jwt.controllers.security;

import io.marklove.spring.security.jwt.constants.ApiUrls;
import io.marklove.spring.security.jwt.constants.AuthConstants;
import io.marklove.spring.security.jwt.exceptions.TokenRefreshException;
import io.marklove.spring.security.jwt.exceptions.ValidatedException;
import io.marklove.spring.security.jwt.payloads.requests.LogOutRequest;
import io.marklove.spring.security.jwt.payloads.requests.LoginRequest;
import io.marklove.spring.security.jwt.payloads.requests.TokenRefreshRequest;
import io.marklove.spring.security.jwt.payloads.responses.ErrorResponse;
import io.marklove.spring.security.jwt.payloads.responses.ValidatedErrorResponse;
import io.marklove.spring.security.jwt.payloads.responses.base.UserInfor;
import io.marklove.spring.security.jwt.payloads.responses.security.JwtResponse;
import io.marklove.spring.security.jwt.payloads.responses.security.RefreshTokenResponse;
import io.marklove.spring.security.jwt.payloads.responses.security.UnAuthResponse;
import io.marklove.spring.security.jwt.persistences.entities.RefreshToken;
import io.marklove.spring.security.jwt.security.JwtUtils;
import io.marklove.spring.security.jwt.security.model.UserDetailsImpl;
import io.marklove.spring.security.jwt.services.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "authentication", description = "the authentication API with documentation annotations")
public class UserController {
  @Autowired
  private RefreshTokenService refreshTokenService;
  
  @PostMapping(ApiUrls.LOG_OUT)
  @Operation(summary = "logout remove refresh-token")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success", content = {@Content()}),
          @ApiResponse(responseCode = "417", description = "Bad request", content =
                  @Content(mediaType = "application/json", schema = @Schema(implementation = ValidatedErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
  public ResponseEntity<?> logout(@Valid @RequestBody LogOutRequest logOutRequest) {
    refreshTokenService.deleteByUserId(logOutRequest.getUserId());
    return ResponseEntity.ok(null);
  }
}