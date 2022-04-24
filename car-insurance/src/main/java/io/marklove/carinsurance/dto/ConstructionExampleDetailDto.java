package io.marklove.carinsurance.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marklove.carinsurance.dto.converters.AbsoluteUrlsSerializer;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ConstructionExampleDetailDto {
    private long id;

    private MyConstructionExampleDto.CarTypeDto carType;

    private LocalDateTime completedDate;

    @JsonSerialize(using = AbsoluteUrlsSerializer.class)
    private Set<FileInfo> images;

    private String content;

    private String companyName;

    private long companyId;

    @Getter
    @Setter
    static class CarTypeDto {
        private long id;
        private String carInfo;
    }
}
