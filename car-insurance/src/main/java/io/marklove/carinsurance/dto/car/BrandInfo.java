package io.marklove.carinsurance.dto.car;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marklove.carinsurance.dto.converters.AbsoluteUrlSerializer;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandInfo implements Serializable {

    private long id;

    private String brandName;

    @JsonSerialize(using = AbsoluteUrlSerializer.class)
    private FileInfo attachFile;
}
