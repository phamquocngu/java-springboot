package io.marklove.carinsurance.controller.admin;

import io.marklove.carinsurance.constant.TransactionStatus;
import io.marklove.carinsurance.dto.admin.QuoteDeliveredHistoryDto;
import io.marklove.carinsurance.dto.admin.TransactionDetailDto;
import io.marklove.carinsurance.dto.admin.TransactionDto;
import io.marklove.carinsurance.exception.CommonException;
import io.marklove.carinsurance.exception.ErrorCode;
import io.marklove.carinsurance.repository.CompanyQuoteRepository;
import io.marklove.carinsurance.repository.TransactionRepository;
import io.marklove.carinsurance.service.TransactionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/transactions")
@Api(tags = {"Transaction"})
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository transactionRepo;
    private final CompanyQuoteRepository companyQuoteRepo;
    private final ModelMapper mapper;
    private final TransactionService transactionService;

    @GetMapping
    public Page<TransactionDto> list(@RequestParam(required = false) TransactionStatus status,
                                     @RequestParam(required = false) String transactionIn,
                                     @RequestParam(required = false) String memberId,
                                     Pageable page) {

        Long transactionId = null;
        try {
            if (transactionIn != null && transactionIn.length() > 0) {
                transactionId = Long.parseLong(transactionIn);
            }
        } catch (NumberFormatException ex) {
            transactionId = -1L;
        }
        return transactionRepo.find(
                    status,
                    transactionId,
                    memberId,
                    page
                )
                .map(transaction -> {
                    var dto = mapper.map(transaction, TransactionDto.class);
                    dto.setConstructionType(transactionService.getConstructionType(transaction));
                    return dto;
                });
    }

    @GetMapping("/{transactionId}")
    public TransactionDetailDto detail(@PathVariable long transactionId) throws IllegalAccessException {
        var transaction = transactionRepo.findById(transactionId)
                .orElseThrow(() -> new CommonException(ErrorCode.ENTITY_NOT_FOUND));

        var dto = mapper.map(transaction, TransactionDetailDto.class);
        dto.setConstructionType(transactionService.getConstructionType(transaction));
        dto.setConstructionPart(transactionService.getConstructionPart(transaction));
        return dto;
    }

    @GetMapping("/quote-history/{quoteId}")
    public QuoteDeliveredHistoryDto getDetailQuote(@PathVariable long quoteId) {
        var quote = companyQuoteRepo.findById(quoteId)
                .orElseThrow(() -> new CommonException(ErrorCode.QUOTATION_NOT_FOUND));

        return mapper.map(quote, QuoteDeliveredHistoryDto.class);
    }
}
