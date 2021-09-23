package io.marklove.spring.security.jwt.controllers.security;

import io.marklove.spring.security.jwt.constants.ApiUrls;
import io.marklove.spring.security.jwt.payloads.requests.security.SignupReq;
import io.marklove.spring.security.jwt.payloads.requests.security.VerifySignupReq;
import io.marklove.spring.security.jwt.payloads.responses.error.ErrorResponse;
import io.marklove.spring.security.jwt.payloads.responses.security.TokenVerifyRes;
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
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenVerifyRes.class))}),
        @ApiResponse(responseCode = "417", description = "Bad request", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupReq signupReq) {

        String token = verifyTokenService.register(signupReq);

        return ResponseEntity.ok(new TokenVerifyRes(token));
    }

    @PutMapping(ApiUrls.VERIFY)
    @Operation(summary = "verify register by token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = {@Content()}),
        @ApiResponse(responseCode = "417", description = "Bad request", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> verifyRegister(@Valid @RequestParam(required = true, value = "token") VerifySignupReq token) {

        verifyTokenService.verifyRegister(token.getToken());

        return ResponseEntity.ok(null);
    }
}
