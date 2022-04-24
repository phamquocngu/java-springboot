package io.marklove.carinsurance.dto.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class PasswordChangeDto {

    @NotBlank
    private String newPassword;
}
