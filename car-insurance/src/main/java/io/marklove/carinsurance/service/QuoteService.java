package io.marklove.carinsurance.service;

import io.marklove.carinsurance.dto.company.*;
import io.marklove.carinsurance.dto.enums.EQuoteStatusFilter;
import io.marklove.carinsurance.dto.quote.TransactionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface QuoteService {

    Page<QuoteList> getAllQuotesByStatus(EQuoteStatusFilter statusFilter, Pageable pageable);

    List<Long> getAllQuotesByTransactionId(long id);

    TransactionRequest quoteRequestDetail(long companyQuoteId);

    void deliver(CompanyQuoteRegistration dto);

    CompanyQuoteRegistration deliveredQuote(long id);

    void changeStatus(long id, QuoteActionReq action);

    TotalSalesDto getTotalSales(LocalDate start, LocalDate end);

    Page<SalesInfo> getSalesInfo(LocalDate start, LocalDate end, Pageable pageable);

    QuoteIdsByStatus getQuotesByStatus();

    void confirmRequest(long id);
}
