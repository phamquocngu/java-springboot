package io.marklove.carinsurance.dto.admin;

import io.marklove.carinsurance.constant.ShowStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SaveBannerDto {
    @Min(1)
    private int position;

    @NotNull
    private ShowStatus status;

    private String connectionLink;

    private boolean imgChanged = true;
}