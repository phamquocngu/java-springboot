package io.marklove.carinsurance.dto.car;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marklove.carinsurance.constant.enums.EProductType;
import io.marklove.carinsurance.dto.converters.AbsoluteUrlSerializer;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarTypeDetail extends CarTypeInfo {

    @NotNull
    private EProductType productType;

    @NotNull
    private BrandInfo brand;

    private ModelInfo model;

    @NotNull
    @JsonSerialize(using = AbsoluteUrlSerializer.class)
    private FileInfo attachFile;

    private LocalDateTime createdOn;
}
