package io.marklove.spring.security.jwt.controllers.security;

import io.marklove.spring.security.jwt.constants.ApiUrls;
import io.marklove.spring.security.jwt.constants.MessageCode;
import io.marklove.spring.security.jwt.exceptions.BusinessException;
import io.marklove.spring.security.jwt.payloads.requests.CreateUserRequest;
import io.marklove.spring.security.jwt.payloads.responses.ErrorResponse;
import io.marklove.spring.security.jwt.payloads.responses.ValidatedErrorResponse;
import io.marklove.spring.security.jwt.persistences.entities.Role;
import io.marklove.spring.security.jwt.persistences.entities.User;
import io.marklove.spring.security.jwt.enums.ERole;
import io.marklove.spring.security.jwt.persistences.repository.RoleRepository;
import io.marklove.spring.security.jwt.persistences.repository.UserRepository;
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
@Tag(name = "admin controller", description = "the APIs for role admin")
public class AdminController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping(ApiUrls.CREATE_USER)
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
            throw new BusinessException(MessageCode.Error.code5000, null);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BusinessException(MessageCode.Error.code5001, null);
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEnable() == null ? true : signUpRequest.getEnable(),
                signUpRequest.getAccountExpired() == null ? false : signUpRequest.getAccountExpired(),
                signUpRequest.getAccountLocked() == null ? false : signUpRequest.getAccountLocked(),
                signUpRequest.getCredentialsExpired() == null ? false : signUpRequest.getCredentialsExpired());

        Set<ERole> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new BusinessException(MessageCode.Error.code5002, null));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case ADMIN:
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new BusinessException(MessageCode.Error.code5002, null));
                        roles.add(adminRole);

                        break;
                    case MODERATOR:
                        Role modRole = roleRepository.findByName(ERole.MODERATOR)
                                .orElseThrow(() -> new BusinessException(MessageCode.Error.code5002, null));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.USER)
                                .orElseThrow(() -> new BusinessException(MessageCode.Error.code5002, null));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(null);
    }
}
