package io.marklove.carinsurance.controller.app;


import io.marklove.carinsurance.api.IamportAPI;
import io.marklove.carinsurance.constant.PaymentStatus;
import io.marklove.carinsurance.dto.payment.FinishPaymentDto;
import io.marklove.carinsurance.dto.payment.InitPaymentDto;
import io.marklove.carinsurance.dto.payment.InitPaymentRes;
import io.marklove.carinsurance.entity.Member;
import io.marklove.carinsurance.entity.PaymentHistory;
import io.marklove.carinsurance.exception.CommonException;
import io.marklove.carinsurance.exception.ErrorCode;
import io.marklove.carinsurance.repository.MemberRepository;
import io.marklove.carinsurance.repository.PaymentHistoryRepository;
import io.marklove.carinsurance.security.AuthenticationFacade;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app/payments")
@Api(tags = {"App Payment"})
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentHistoryRepository paymentHistoryRepo;
    private final MemberRepository memberRepo;

    private final IamportAPI iamportAPI;
    private final AuthenticationFacade authFacade;

    @PostMapping("/init")
    @ResponseStatus(HttpStatus.CREATED)
    InitPaymentRes initPayment(@RequestBody InitPaymentDto dto) {
        final var userId = authFacade.getAuthentication().getUserId();

        long amount = dto.getPoints();
        PaymentHistory ph = PaymentHistory.builder().status(PaymentStatus.PENDING).member(new Member(userId)).amount(
                amount).build();

        ph = paymentHistoryRepo.save(ph);

        return InitPaymentRes.builder()
                             .amount(ph.getAmount())
                             .points(dto.getPoints())
                             .orderId(String.valueOf(ph.getId()))
                             .build();
    }

    @PostMapping("/complete")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    void finishPayment(@RequestBody FinishPaymentDto dto) {

        var paymentInfo = iamportAPI.getPaymentInfo(dto.getImpUid()).getResponse();

        var ph = paymentHistoryRepo.findById(Long.parseLong(dto.getMerchantUid())).orElseThrow();

        if(ph.getStatus() != PaymentStatus.PENDING)
            throw new CommonException(ErrorCode.PAYMENT_ILLEGAL_STATE);

        if(paymentInfo.getAmount() != ph.getAmount())
            throw new CommonException(ErrorCode.PAYMENT_AMOUNT_NOT_MATCHED);

        switch (paymentInfo.getStatus()) {
            case "ready":
                break;
            case "paid":
                ph.setStatus(PaymentStatus.COMPLETED);
                var member = ph.getMember();
                member.setPoint(member.getPoint() + paymentInfo.getAmount());
                memberRepo.save(member);
                ph = paymentHistoryRepo.save(ph);
                break;
        }
    }
}
