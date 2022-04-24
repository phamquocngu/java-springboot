package io.marklove.carinsurance.service;

import io.marklove.carinsurance.dto.quote.*;
import io.marklove.carinsurance.entity.transaction.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    String getConstructionType(Transaction transaction);

    List<String> getConstructionPart(Transaction transaction) throws IllegalAccessException;

    Page<TransactionList> getAllByCurrentUser(Pageable pageable);

    TransactionRequest getTransactionDetail(long id);

    QuotationList getQuotations(long id);

    CompanyQuoteDetail getQuotationDetail(long id);

    boolean isPermission(long transactionId);

    void applyReservation(long quoteId, LocalDateTime reservationDate);

    void cancelReservation(long quoteId, String reason);

    void deleteReservation(long quoteId);

    boolean checkAllowedApplyReservation(long quoteId);

    ReservationHistory reservationHistory(long quoteId);
}
