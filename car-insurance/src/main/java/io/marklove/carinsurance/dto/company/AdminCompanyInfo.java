package io.marklove.carinsurance.dto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AdminCompanyInfo implements Serializable {
    @JsonProperty("companyCode")
    private long id;

    private String companyName;

    private String companyId;
}
