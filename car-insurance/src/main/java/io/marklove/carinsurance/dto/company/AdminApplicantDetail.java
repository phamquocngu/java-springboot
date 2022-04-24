package io.marklove.carinsurance.dto.company;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marklove.carinsurance.dto.converters.AbsoluteUrlSerializer;
import io.marklove.carinsurance.dto.converters.ConstructableTypeSerializer;
import io.marklove.carinsurance.dto.converters.EntryRouteSerializer;
import io.marklove.carinsurance.entity.embeddable.ConstructableType;
import io.marklove.carinsurance.entity.embeddable.EntryRoute;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminApplicantDetail extends AdminApplicant {

    private String representativeName;

    private AdminAddress address;

    private String workingTime;

    @JsonSerialize(using = AbsoluteUrlSerializer.class)
    private FileInfo attachFile;

    @JsonSerialize(using = ConstructableTypeSerializer.class)
    private ConstructableType constructableType;

    @JsonSerialize(using = EntryRouteSerializer.class)
    private EntryRoute entryRoute;

    private String reason;
}
