package io.marklove.carinsurance.controller.app;

import io.marklove.carinsurance.api.solapi.PhoneVerifyDto;
import io.marklove.carinsurance.api.solapi.otp.OtpService;
import io.marklove.carinsurance.exception.CommonException;
import io.marklove.carinsurance.exception.ErrorCode;
import io.marklove.carinsurance.repository.MemberRepository;
import io.marklove.carinsurance.security.AuthenticationFacade;
import io.marklove.carinsurance.utils.AesUtils;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/app/auth")
@Api(tags = {"SMS Authentication"})
@Log4j2
public class SolapiController {


    private final AesUtils aesUtils;

    private final OtpService otpService;

    private final MemberRepository memberRepo;

    private final AuthenticationFacade authenticationFacade;

    public SolapiController(AesUtils aesUtils, OtpService otpService, MemberRepository memberRepo, AuthenticationFacade authenticationFacade) {
        this.aesUtils = aesUtils;
        this.otpService = otpService;
        this.memberRepo = memberRepo;
        this.authenticationFacade = authenticationFacade;
    }


    @GetMapping(path = "/request-to-verify", produces = MediaType.TEXT_PLAIN_VALUE)
    public String sendPhoneVerification(@RequestParam String phoneNo) throws Exception {
        Boolean exitByPhone = memberRepo.exitByPhone(phoneNo);
        if (exitByPhone) {
            throw new CommonException(ErrorCode.PHONE_EXITED);
        } else {
            return otpService.generalOtp(phoneNo);
        }
    }

    @PostMapping("/verify-code")
    public void confirmVerificationNo(@RequestBody @Valid PhoneVerifyDto dto) throws Exception {

        final String verifyStr = aesUtils.decryptWithPrefixIV(dto.getSig());
        var code = verifyStr.substring(0, verifyStr.indexOf('-'));
        var phone = verifyStr.substring(verifyStr.indexOf('-') + 1);

        if (!phone.equals(dto.getPhoneNo()) || !code.equals(dto.getVerifyNo()))
            throw new CommonException(ErrorCode.PHONE_CODE_INCORRECT);
        else if (dto.getExpTime()) {
            throw new CommonException(ErrorCode.PHONE_CODE_EXPIRED_TIME);
        } else {
            var userDetail = authenticationFacade.getAuthentication();
            var member = memberRepo.findById(userDetail.getUserId()).orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
            member.setPhone(phone);
            memberRepo.save(member);
        }
    }
}
