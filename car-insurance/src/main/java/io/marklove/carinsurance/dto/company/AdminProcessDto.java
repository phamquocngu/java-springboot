package io.marklove.carinsurance.dto.company;

import io.marklove.carinsurance.dto.enums.ECompanyRegistrationProcess;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class AdminProcessDto implements Serializable {

    @NotNull
    private ECompanyRegistrationProcess action;

    private String reason;
}
