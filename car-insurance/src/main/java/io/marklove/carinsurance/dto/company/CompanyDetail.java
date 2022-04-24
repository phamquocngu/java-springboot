package io.marklove.carinsurance.dto.company;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marklove.carinsurance.constant.enums.ERegistrationStatus;
import io.marklove.carinsurance.dto.ConstructionExDto;
import io.marklove.carinsurance.dto.converters.AbsoluteUrlSerializer;
import io.marklove.carinsurance.entity.embeddable.Address;
import io.marklove.carinsurance.entity.embeddable.ConstructableType;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetail implements Serializable {

    @JsonSerialize(using = AbsoluteUrlSerializer.class)
    private FileInfo attachFile;

    private ERegistrationStatus processingStatus;

    private String companyName;

    private Address address;

    private String workingTime;

    private ConstructableType constructableType;

    private Long totalReview;

    private float constructionQuality;

    private float average;

    private float kindness;

    private float explainProduct;

    private String introduction;

    private List<ConstructionExDto> constructionExamples;
}
