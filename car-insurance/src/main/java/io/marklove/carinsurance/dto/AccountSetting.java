package io.marklove.carinsurance.dto;

import io.marklove.carinsurance.constant.enums.EGender;
import io.marklove.carinsurance.constant.enums.ESNSType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AccountSetting {

    private String memberId;

    private EGender gender;

    private LocalDate birthday;

    private String phone;

    private ESNSType sns;
}