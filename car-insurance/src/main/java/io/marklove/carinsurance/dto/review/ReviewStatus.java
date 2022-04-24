package io.marklove.carinsurance.dto.review;

import io.marklove.carinsurance.constant.ShowStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReviewStatus {

    private long id;

    @NotNull
    private ShowStatus status;
}