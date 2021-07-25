package io.marklove.spring.security.jwt.controllers.security;

import io.marklove.spring.security.jwt.constants.ApiUrls;
import io.marklove.spring.security.jwt.payloads.requests.security.ReqLogOut;
import io.marklove.spring.security.jwt.payloads.responses.error.ErrorResponse;
import io.marklove.spring.security.jwt.services.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Tag(name = "user", description = "the APIs for user")
@RequestMapping(ApiUrls.USER)
public class UserController {
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping(ApiUrls.LOG_OUT)
    @Operation(summary = "logout remove refresh-token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = {@Content()}),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "417", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> logout(@Valid @RequestBody ReqLogOut logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok(null);
    }
}