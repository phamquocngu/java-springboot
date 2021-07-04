package io.marklove.spring.security.jwt.controllers.security;

import io.marklove.spring.security.jwt.constants.MessageCode;
import io.marklove.spring.security.jwt.exception.TokenRefreshException;
import io.marklove.spring.security.jwt.exception.ValidatedException;
import io.marklove.spring.security.jwt.payload.request.LogOutRequest;
import io.marklove.spring.security.jwt.payload.request.LoginRequest;
import io.marklove.spring.security.jwt.payload.request.TokenRefreshRequest;
import io.marklove.spring.security.jwt.payload.response.*;
import io.marklove.spring.security.jwt.payload.response.base.UserInfor;
import io.marklove.spring.security.jwt.persistence.entities.RefreshToken;
import io.marklove.spring.security.jwt.security.jwt.JwtUtils;
import io.marklove.spring.security.jwt.security.services.RefreshTokenService;
import io.marklove.spring.security.jwt.security.services.impl.UserDetailsImpl;
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
@RequestMapping("/api/auth")
@Tag(name = "authentication", description = "the authentication API with documentation annotations")
public class AuthController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private RefreshTokenService refreshTokenService;

  @PostMapping("/signin")
  @Operation(summary = "get jwt token")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Success", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))}),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content =
                  @Content(mediaType = "application/json", schema = @Schema(implementation = UnAuthResponse.class))),
          @ApiResponse(responseCode = "417", description = "Bad request", content =
                  @Content(mediaType = "application/json", schema = @Schema(implementation = ValidatedErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
  public ResponseEntity<?> authenticateUser(@Validated @RequestBody final LoginRequest loginRequest, BindingResult errors) {

    if (errors.hasErrors()) {
      throw new ValidatedException(errors);
    }

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);

    Set<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        .collect(Collectors.toSet());

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

    return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(),
       new UserInfor(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles)));
  }

  @PostMapping("/refresh-token")
  @Operation(summary = "get new token by refresh-token")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Success", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))}),
          @ApiResponse(responseCode = "403", description = "Forbidden", content =
                  @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "417", description = "Bad request", content =
                  @Content(mediaType = "application/json", schema = @Schema(implementation = ValidatedErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String token = jwtUtils.generateTokenFromUsername(user.getUsername());
          return ResponseEntity.ok(new RefreshTokenResponse(token, requestRefreshToken));
        })
        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
            MessageCode.Auth.code4100));
  }
  
  @PostMapping("/logout")
  @Operation(summary = "logout remove refresh-token")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success", content = {@Content()}),
          @ApiResponse(responseCode = "417", description = "Bad request", content =
                  @Content(mediaType = "application/json", schema = @Schema(implementation = ValidatedErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
  public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
    refreshTokenService.deleteByUserId(logOutRequest.getUserId());
    return ResponseEntity.ok(null);
  }
}