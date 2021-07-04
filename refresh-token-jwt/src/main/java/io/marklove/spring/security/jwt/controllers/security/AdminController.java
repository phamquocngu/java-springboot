package io.marklove.spring.security.jwt.controllers.security;

import io.marklove.spring.security.jwt.constants.MessageCode;
import io.marklove.spring.security.jwt.exception.BusinessException;
import io.marklove.spring.security.jwt.payload.request.CreateUserRequest;
import io.marklove.spring.security.jwt.payload.response.ErrorResponse;
import io.marklove.spring.security.jwt.payload.response.JwtResponse;
import io.marklove.spring.security.jwt.payload.response.ValidatedErrorResponse;
import io.marklove.spring.security.jwt.persistence.entities.Role;
import io.marklove.spring.security.jwt.persistence.entities.User;
import io.marklove.spring.security.jwt.persistence.enums.ERole;
import io.marklove.spring.security.jwt.persistence.repository.RoleRepository;
import io.marklove.spring.security.jwt.persistence.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ngupq
 */
@RestController
@RequestMapping("/api/admin")
@Tag(name = "admin controller", description = "the APIs for role admin")
public class AdminController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/create-user")
    @Operation(summary = "create new a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content()}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "417", description = "Bad request", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidatedErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest signUpRequest) throws Exception {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new BusinessException(MessageCode.Error.code5000);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BusinessException(MessageCode.Error.code5001);
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEnable() == null ? true : signUpRequest.getEnable(),
                signUpRequest.getAccountExpired() == null ? false : signUpRequest.getAccountExpired(),
                signUpRequest.getAccountLocked() == null ? false : signUpRequest.getAccountLocked(),
                signUpRequest.getCredentialsExpired() == null ? false : signUpRequest.getCredentialsExpired());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new BusinessException(MessageCode.Error.code5002));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new BusinessException(MessageCode.Error.code5002));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new BusinessException(MessageCode.Error.code5002));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new BusinessException(MessageCode.Error.code5002));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(null);
    }
}
