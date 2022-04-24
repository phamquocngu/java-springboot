package io.marklove.carinsurance.dto;

import io.marklove.carinsurance.constant.ShowStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstructionExStatusDto {
    private long id;

    private ShowStatus status;
}
