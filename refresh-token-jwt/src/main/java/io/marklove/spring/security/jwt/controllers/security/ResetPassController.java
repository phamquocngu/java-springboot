package io.marklove.spring.security.jwt.controllers.security;

import io.marklove.spring.security.jwt.constants.ApiUrls;
import io.marklove.spring.security.jwt.exceptions.BusinessException;
import io.marklove.spring.security.jwt.payloads.requests.ResetPasswordRequest;
import io.marklove.spring.security.jwt.payloads.requests.SignupRequest;
import io.marklove.spring.security.jwt.payloads.responses.ErrorResponse;
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
@Tag(name = "reset-password", description = "reset-password, verify reset-password by token")
@RequestMapping(ApiUrls.RESET_PASS)
public class ResetPassController {
    @Autowired
    private VerifyTokenService verifyTokenService;

    @PostMapping(ApiUrls.REQUEST)
    @Operation(summary = "reset password: return a token to send email verify")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = TokenVerifyResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = BusinessException.class)))})
    public ResponseEntity<?> rsPassword(@RequestParam(required = true, value = "email") String email) {
        String token = verifyTokenService.resetPassword(email);

        return ResponseEntity.ok(new TokenVerifyResponse(token));
    }

    @PutMapping(ApiUrls.VERIFY)
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
