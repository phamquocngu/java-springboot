package io.marklove.carinsurance.projection.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marklove.carinsurance.constant.enums.ESNSType;
import io.marklove.carinsurance.dto.converters.TimeZoneSerializer;

import java.time.LocalDateTime;

public interface MemberView {

    long getId();

    String getName();

    String getMemberId();

    String getPhone();

    ESNSType getSns();

    @JsonSerialize(using = TimeZoneSerializer.class)
    LocalDateTime getLastLoggedIn();

    @JsonSerialize(using = TimeZoneSerializer.class)
    LocalDateTime getCreatedOn();

    boolean getCompanyMember();

    CompanyGroupView getGroup();

    interface CompanyGroupView {
        long getId();

        String getName();
    }
}
