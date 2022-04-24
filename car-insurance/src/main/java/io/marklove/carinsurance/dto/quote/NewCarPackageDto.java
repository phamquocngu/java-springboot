package io.marklove.carinsurance.dto.quote;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.marklove.carinsurance.constant.enums.EConstructionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName(EConstructionType.JsonProp.NEW_CAR_PACKAGE)
public class NewCarPackageDto extends TransactionRequest {

    private boolean tinting;

    private boolean blackBox;

    private boolean ppf;

    private boolean grassFilmCoating;

    private boolean polish;

    private boolean lowerSoundproofing;

    private boolean outsideSoundproofing;
}
