package io.marklove.carinsurance.service;

import io.marklove.carinsurance.constant.enums.ENoticeType;
import io.marklove.carinsurance.entity.Member;

public interface NoticeService {

    void createNotice(ENoticeType type, Member recipient, String content, Long detailId);
}
