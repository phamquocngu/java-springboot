package io.marklove.carinsurance.dto.car;

import io.marklove.carinsurance.constant.enums.EProductType;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarTypeRegistration extends CarTypeInfo {

    @NotNull
    private EProductType productType;

    private long brandId;

    private long modelId;

    @NotNull
    private FileInfo attachFile;
}
