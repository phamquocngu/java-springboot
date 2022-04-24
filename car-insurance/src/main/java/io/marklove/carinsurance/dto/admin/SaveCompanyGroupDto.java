package io.marklove.carinsurance.dto.admin;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class SaveCompanyGroupDto {
    private long id;

    @Min(0)
    private int fee;

    @Min(0)
    private int deliveryCost;
}
