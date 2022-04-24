package io.marklove.carinsurance.service;

import io.marklove.carinsurance.dto.AccountSetting;
import io.marklove.carinsurance.dto.FAQInfo;
import io.marklove.carinsurance.dto.MemberInfo;
import io.marklove.carinsurance.entity.embeddable.NoticeSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyPageService {

    MemberInfo getMemberInfo();

    AccountSetting getAccountInfo();

    NoticeSetting getNoticeSetting();

    void setupNoticeSetting(NoticeSetting setting);

    Page<FAQInfo> getFAQs(Pageable pageable);

    void withdraw();
}
