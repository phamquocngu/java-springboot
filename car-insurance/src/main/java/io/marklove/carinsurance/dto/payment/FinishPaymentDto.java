package io.marklove.carinsurance.dto.payment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FinishPaymentDto {

    private String impUid;

    private String merchantUid;

    private String orderId;
}
