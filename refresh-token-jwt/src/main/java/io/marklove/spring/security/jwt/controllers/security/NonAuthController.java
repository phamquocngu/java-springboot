package io.marklove.spring.security.jwt.controllers.security;

import io.marklove.spring.security.jwt.exception.BusinessException;
import io.marklove.spring.security.jwt.payload.request.ResetPasswordRequest;
import io.marklove.spring.security.jwt.payload.request.SignupRequest;
import io.marklove.spring.security.jwt.payload.response.*;
import io.marklove.spring.security.jwt.security.services.VerifyTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ngupq
 */
@RestController
@RequestMapping("/api/non-auth")
@Tag(name = "signup, reset-password", description = "register, verify register by token, reset-password, verify reset-password by token")
public class NonAuthController {
    @Autowired
    private VerifyTokenService verifyTokenService;

    @PostMapping("/signup/register")
    @Operation(summary = "register: return a token to send email verify")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "417", description = "Bad request", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidatedErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        String token = verifyTokenService.register(signUpRequest);

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PutMapping(value = "/signup/verify")
    @Operation(summary = "verify register by token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {@Content()}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessException.class)))})
    public ResponseEntity<?> verifyRegister(@RequestParam(required = true, value = "token") String token) {

        verifyTokenService.verifyRegister(token);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/reset-password/reset")
    @Operation(summary = "reset password: return a token to send email verify")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessException.class)))})
    public ResponseEntity<?> rsPassword(@RequestParam(required = true, value = "email") String email) {
        String token = verifyTokenService.resetPassword(email);

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PutMapping(value = "/reset-password/verify")
    @Operation(summary = "verify reset password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {@Content()}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessException.class)))})
    public ResponseEntity<?> verifyRsPassword(@RequestBody @Validated ResetPasswordRequest resetPasswordRequest) {

        verifyTokenService.verifyResetPassword(resetPasswordRequest);

        return ResponseEntity.ok(null);
    }
}
