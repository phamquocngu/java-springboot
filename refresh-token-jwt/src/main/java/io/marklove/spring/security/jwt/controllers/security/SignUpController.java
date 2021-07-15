package io.marklove.spring.security.jwt.controllers.security;

import io.marklove.spring.security.jwt.constants.ApiUrls;
import io.marklove.spring.security.jwt.exceptions.BusinessException;
import io.marklove.spring.security.jwt.payloads.requests.ResetPasswordRequest;
import io.marklove.spring.security.jwt.payloads.requests.SignupRequest;
import io.marklove.spring.security.jwt.payloads.responses.*;
import io.marklove.spring.security.jwt.payloads.responses.TokenVerifyResponse;
import io.marklove.spring.security.jwt.payloads.responses.ValidatedErrorResponse;
import io.marklove.spring.security.jwt.services.VerifyTokenService;
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
@Tag(name = "sign-up", description = "register, verify register by token")
@RequestMapping(ApiUrls.SIGN_UP)
public class SignUpController {
    @Autowired
    private VerifyTokenService verifyTokenService;

    @PostMapping(ApiUrls.REGISTER)
    @Operation(summary = "register: return a token to send email verify")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokenVerifyResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "417", description = "Bad request", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidatedErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        String token = verifyTokenService.register(signUpRequest);

        return ResponseEntity.ok(new TokenVerifyResponse(token));
    }

    @PutMapping(ApiUrls.VERIFY)
    @Operation(summary = "verify register by token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {@Content()}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessException.class)))})
    public ResponseEntity<?> verifyRegister(@RequestParam(required = true, value = "token") String token) {

        verifyTokenService.verifyRegister(token);

        return ResponseEntity.ok(null);
    }
}
