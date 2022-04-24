package io.marklove.carinsurance.dto.admin;

import io.marklove.carinsurance.constant.enums.EQuoteStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuoteDeliveredHistoryDto {

    private CompanyDto company;

    private EQuoteStatus status;

    private int constructionFee;

    private int paymentAmount;

    private String estConstructionPeriod;

    private LocalDateTime reservationDate;

    private String notes;

    private String reason;


    @Getter
    @Setter
    static class CompanyDto {
        private String companyName;
    }
}
