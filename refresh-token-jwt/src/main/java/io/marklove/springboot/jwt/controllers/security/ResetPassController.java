package io.marklove.springboot.jwt.controllers.security;

import io.marklove.springboot.jwt.constants.ApiUrls;
import io.marklove.springboot.jwt.payloads.requests.security.ResetPassReq;
import io.marklove.springboot.jwt.payloads.requests.security.VerifyResetPassReq;
import io.marklove.springboot.jwt.payloads.responses.error.ErrorResponse;
import io.marklove.springboot.jwt.payloads.responses.security.TokenVerifyRes;
import io.marklove.springboot.jwt.services.VerifyTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenVerifyRes.class))}),
        @ApiResponse(responseCode = "417", description = "Bad Request", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> rsPassword(@Validated @RequestParam(required = true, value = "email") ResetPassReq email) {
        String token = verifyTokenService.resetPassword(email.getEmail());

        return ResponseEntity.ok(new TokenVerifyRes(token));
    }

    @PutMapping(ApiUrls.VERIFY)
    @Operation(summary = "verify reset password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "success", content = {@Content()}),
        @ApiResponse(responseCode = "417", description = "Bad Request", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> verifyRsPassword(@Validated @RequestBody VerifyResetPassReq verifyResetPassReq) {

        verifyTokenService.verifyResetPassword(verifyResetPassReq);

        return ResponseEntity.ok(null);
    }
}
