package io.marklove.carinsurance.dto.review;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marklove.carinsurance.dto.car.CarInfoDto;
import io.marklove.carinsurance.dto.converters.TimeZoneSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserReviewDto {
    private long id;

    private CarInfoDto carInfo;

    private int quality;

    private int kindness;

    private int productExplain;

    private String content;

    @JsonSerialize(using = TimeZoneSerializer.class)
    private LocalDateTime createdOn;
}
