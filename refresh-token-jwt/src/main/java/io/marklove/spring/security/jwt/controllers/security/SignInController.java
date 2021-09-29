package io.marklove.spring.security.jwt.controllers.security;

import io.marklove.spring.security.jwt.constants.ApiUrls;
import io.marklove.spring.security.jwt.constants.AuthConstants;
import io.marklove.spring.security.jwt.enums.EGrantType;
import io.marklove.spring.security.jwt.exceptions.CommonException;
import io.marklove.spring.security.jwt.exceptions.TokenRefreshException;
import io.marklove.spring.security.jwt.payloads.requests.security.LoginReq;
import io.marklove.spring.security.jwt.payloads.responses.error.ErrorResponse;
import io.marklove.spring.security.jwt.payloads.responses.security.JwtRes;
import io.marklove.spring.security.jwt.payloads.responses.security.UserInfo;
import io.marklove.spring.security.jwt.persistences.entities.RefreshToken;
import io.marklove.spring.security.jwt.persistences.entities.User;
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
@RequestMapping(ApiUrls.SIGN_IN)
public class SignInController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/")
    @Operation(summary = "get jwt token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = JwtRes.class))}),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "417", description = "Bad request", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody final LoginReq loginReq) {

        String accessToken;
        String refreshToken;
        UserInfo userInfo;
        if(loginReq.getGrantType() == EGrantType.CLIENT_CREDENTIALS) {
            //authentication by access token
            Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Set<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toSet());

            userInfo = new UserInfo(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
            accessToken = jwtUtils.generateJwtToken(userDetails.getUsername());
            refreshToken = refreshTokenService.createRefreshToken(userDetails.getId()).getToken();
        } else if(loginReq.getGrantType() == EGrantType.REFRESH_TOKEN) {
            //authentication by refresh token
            RefreshToken refreshTokenEntity = refreshTokenService.findByToken(loginReq.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(refreshTokenService::resetRefreshToken)
                .orElseThrow(() -> new TokenRefreshException(AuthConstants.Error.INVALID_REFRESH_TOKEN));

            User user = refreshTokenEntity.getUser();
            Set<String> roles = user.getRoles().stream().map(item -> item.getName().name())
                .collect(Collectors.toSet());
            userInfo = new UserInfo(user.getId(), user.getUsername(), user.getEmail(), roles);
            accessToken = jwtUtils.generateJwtToken(user.getUsername());
            refreshToken = refreshTokenEntity.getToken();
        } else {
            throw new CommonException(AuthConstants.Error.GRANT_TYPE_NOT_SUPPORT, null);
        }

        return ResponseEntity.ok(new JwtRes(accessToken, refreshToken, userInfo));
    }
}