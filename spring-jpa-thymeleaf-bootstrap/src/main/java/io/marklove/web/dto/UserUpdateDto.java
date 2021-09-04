package io.marklove.web.dto;

import io.marklove.validators.annotation.ValidEmail;
import io.marklove.domain.entity.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ngupq
 */
@Data
public class UserUpdateDto {

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

    private List<Role> roles = new ArrayList<>();

    private boolean enabled;

    public UserUpdateDto(Long id, String name, String surname, String username, String email, boolean enabled) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
    }

    public UserUpdateDto(Long id, String name, String surname, String username, String email, boolean enabled,
                         List<Role> roles) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.roles = roles;
    }

}