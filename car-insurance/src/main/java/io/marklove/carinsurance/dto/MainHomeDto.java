package io.marklove.carinsurance.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marklove.carinsurance.dto.car.CarInfoDto;
import io.marklove.carinsurance.dto.converters.AbsoluteUrlSerializer;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainHomeDto {
    @JsonSerialize(using = AbsoluteUrlSerializer.class)
    private FileInfo carImage;

    private CarInfoDto carInfo;

    private List<ConstructionExDto> examples;

    private List<BannerDto> banners;
}
