package io.marklove.carinsurance.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.marklove.carinsurance.constant.enums.ERegistrationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminApplicant extends AdminCompanyInfo {

    private String applicantId;

    private String applicantName;

    private String contact;

    private ERegistrationStatus processingStatus;

    @JsonProperty("accessDate")
    private LocalDateTime registrationDate;
}
