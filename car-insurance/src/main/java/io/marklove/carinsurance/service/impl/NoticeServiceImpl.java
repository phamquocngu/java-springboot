package io.marklove.carinsurance.service.impl;

import io.marklove.carinsurance.constant.enums.ENoticeType;
import io.marklove.carinsurance.entity.AppNotice;
import io.marklove.carinsurance.entity.Member;
import io.marklove.carinsurance.repository.AppNoticeRepository;
import io.marklove.carinsurance.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final AppNoticeRepository appNoticeRepo;

    @Override
    @Transactional
    public void createNotice(ENoticeType type, Member recipient, String content,Long detailId) {
        var notice = new AppNotice();
        notice.setRecipient(recipient);
        notice.setType(type);
        notice.setContent(content);
        notice.setDetailId(detailId);

        appNoticeRepo.save(notice);
    }
}
