package io.marklove.web.dto;

import io.marklove.validators.annotation.PasswordMatches;
import io.marklove.validators.annotation.ValidEmail;
import io.marklove.domain.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author ngupq
 */
@Data
@NoArgsConstructor
@PasswordMatches
public class UserDto {

    private Long id;

    @NotBlank (message = "Name is required")
    private String name;

    @NotBlank (message = "Surname is required")
    private String surname;

    @NotBlank (message = "Username is required")
    private String username;

    @ValidEmail
    @NotBlank (message = "Email is required")
    private String email;

    @NotBlank (message = "Password is required")
    private String password;

    @NotBlank (message = "Matching password is required")
    private String matchingPassword;

    private boolean enabled;

    public UserDto(Long id, String name, String surname, String username, String email, String password, String
            matchingPassword, Set<Role> roles, boolean enabled) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.enabled = enabled;
    }
}
