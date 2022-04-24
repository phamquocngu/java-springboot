package io.marklove.carinsurance.service;

import io.marklove.carinsurance.dto.admin.DashboardDto;
import io.marklove.carinsurance.dto.quote.TransactionRequest;
import io.marklove.carinsurance.entity.embeddable.FileInfo;

import java.util.Set;

public interface MainHomeService {

    DashboardDto dashboard();

    Set<FileInfo> requestQuote(TransactionRequest dto);

}
