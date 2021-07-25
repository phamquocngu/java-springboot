package io.marklove.spring.security.jwt.controllers.security;

import io.marklove.spring.security.jwt.constants.ApiUrls;
import io.marklove.spring.security.jwt.constants.AuthConstants;
import io.marklove.spring.security.jwt.exceptions.TokenRefreshException;
import io.marklove.spring.security.jwt.payloads.requests.security.ReqLogin;
import io.marklove.spring.security.jwt.payloads.requests.security.ReqTokenRefresh;
import io.marklove.spring.security.jwt.payloads.responses.error.ErrorResponse;
import io.marklove.spring.security.jwt.payloads.responses.security.ResJwt;
import io.marklove.spring.security.jwt.payloads.responses.security.ResRefreshToken;
import io.marklove.spring.security.jwt.payloads.responses.security.UserInfor;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "authentication", description = "the authentication API with documentation annotations")
@RequestMapping(ApiUrls.AUTH)
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping(ApiUrls.SIGN_IN)
    @Operation(summary = "get jwt token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResJwt.class))}),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "417", description = "Bad request", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody final ReqLogin reqLogin) {

        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(reqLogin.getUsername(), reqLogin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        Set<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
            .collect(Collectors.toSet());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new ResJwt(jwt, refreshToken.getToken(),
            new UserInfor(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles)));
    }

    @PostMapping(ApiUrls.REFRESH_TOKEN)
    @Operation(summary = "get new token by refresh-token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResJwt.class))}),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "417", description = "Bad request", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> refreshToken(@Valid @RequestBody ReqTokenRefresh request) {

        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                return ResponseEntity.ok(new ResRefreshToken(token, requestRefreshToken));
            })
            .orElseThrow(() -> new TokenRefreshException(AuthConstants.Error.INVALID_REFRESH_TOKEN));
    }
}