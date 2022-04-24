package io.marklove.carinsurance.service.impl;

import io.marklove.carinsurance.constant.NotificationType;
import io.marklove.carinsurance.dto.NotificationDetail;
import io.marklove.carinsurance.dto.NotificationList;
import io.marklove.carinsurance.exception.CommonException;
import io.marklove.carinsurance.exception.ErrorCode;
import io.marklove.carinsurance.repository.NotificationRepository;
import io.marklove.carinsurance.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepo;
    private final ModelMapper mapper;

    @Override
    public Page<NotificationList> getMyPageNotifications(Pageable pageable) {
        return notificationRepo.find(NotificationType.GENERAL, null, pageable)
                .map(n -> mapper.map(n, NotificationList.class));
    }

    @Override
    public Page<NotificationList> getCompanyNotifications(Pageable pageable) {

        return notificationRepo.find(NotificationType.COMPANY, null, pageable)
                .map(n -> mapper.map(n, NotificationList.class));
    }

    @Override
    public NotificationDetail getNotificationDetail(long id) {
        var notification = notificationRepo.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOTIFICATION_NOT_FOUND));

        return mapper.map(notification, NotificationDetail.class);
    }
}
