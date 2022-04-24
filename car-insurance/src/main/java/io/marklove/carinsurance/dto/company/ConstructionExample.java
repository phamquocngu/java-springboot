package io.marklove.carinsurance.dto.company;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marklove.carinsurance.dto.car.CarInfoDto;
import io.marklove.carinsurance.dto.converters.AbsoluteUrlsSerializer;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionExample implements Serializable {

    private long id;

    @JsonSerialize(using = AbsoluteUrlsSerializer.class)
    private Set<FileInfo> images;

    private Member writer;

    @Getter
    @Setter
    static class Member {

        private CarInfoDto carType;
    }
}
