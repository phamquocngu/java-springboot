package io.marklove.carinsurance.dto.quote;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.marklove.carinsurance.constant.enums.EConstructionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName(EConstructionType.JsonProp.WINDSHIELD)
public class WindshieldDto extends TransactionRequest {

    private boolean windshield;
}
