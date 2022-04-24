package io.marklove.carinsurance.dto.quote;

import io.marklove.carinsurance.constant.enums.EQuoteStatus;
import io.marklove.carinsurance.entity.embeddable.Address;

public interface CompanyQuoteInfo {

    long getId();

    Company getCompany();

    EQuoteStatus getStatus();

    int getConstructionFee();

    int getPaymentAmount();

    interface Company {

        long getId();

        String getCompanyName();

        Address getAddress();

        long getTotalReview();

        float getAverage();
    }
}
