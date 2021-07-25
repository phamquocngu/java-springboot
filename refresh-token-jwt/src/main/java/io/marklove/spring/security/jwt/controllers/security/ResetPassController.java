package io.marklove.spring.security.jwt.controllers.security;

import io.marklove.spring.security.jwt.constants.ApiUrls;
import io.marklove.spring.security.jwt.payloads.requests.security.ReqResetPass;
import io.marklove.spring.security.jwt.payloads.requests.security.ReqVerifyResetPass;
import io.marklove.spring.security.jwt.payloads.responses.error.ErrorResponse;
import io.marklove.spring.security.jwt.payloads.responses.security.ResTokenVerify;
import io.marklove.spring.security.jwt.services.VerifyTokenService;
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
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResTokenVerify.class))}),
        @ApiResponse(responseCode = "417", description = "Bad Request", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> rsPassword(@Validated @RequestParam(required = true, value = "email") ReqResetPass email) {
        String token = verifyTokenService.resetPassword(email.getEmail());

        return ResponseEntity.ok(new ResTokenVerify(token));
    }

    @PutMapping(ApiUrls.VERIFY)
    @Operation(summary = "verify reset password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "success", content = {@Content()}),
        @ApiResponse(responseCode = "417", description = "Bad Request", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> verifyRsPassword(@Validated @RequestBody ReqVerifyResetPass reqVerifyResetPass) {

        verifyTokenService.verifyResetPassword(reqVerifyResetPass);

        return ResponseEntity.ok(null);
    }
}
