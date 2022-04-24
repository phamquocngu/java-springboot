package io.marklove.carinsurance.dto.admin;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateCompanyGroupDto {

    @NotBlank
    @Size(max = 20)
    private String name;

}
