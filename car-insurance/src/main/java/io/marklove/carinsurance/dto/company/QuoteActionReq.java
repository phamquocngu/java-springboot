package io.marklove.carinsurance.dto.company;

import io.marklove.carinsurance.dto.enums.EQuoteAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuoteActionReq implements Serializable {

    @NotNull
    private EQuoteAction action;

    private String reason;
}
