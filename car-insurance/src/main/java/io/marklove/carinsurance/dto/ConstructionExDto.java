package io.marklove.carinsurance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marklove.carinsurance.dto.car.CarInfoDto;
import io.marklove.carinsurance.dto.converters.AbsoluteUrlsSerializer;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ConstructionExDto {
    @JsonSerialize(using = AbsoluteUrlsSerializer.class)
    private Set<FileInfo> images;

    @JsonProperty("carInfo")
    private CarInfoDto carType;
}
