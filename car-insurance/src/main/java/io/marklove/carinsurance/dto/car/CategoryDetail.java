package io.marklove.carinsurance.dto.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marklove.carinsurance.dto.converters.AbsoluteUrlSerializer;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetail extends CategoryInfo {

    @JsonProperty("position")
    private int orderCategory;

    private LocalDateTime createdOn;

    @JsonSerialize(using = AbsoluteUrlSerializer.class)
    private FileInfo icon;
}
