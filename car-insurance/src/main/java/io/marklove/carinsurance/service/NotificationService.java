package io.marklove.carinsurance.service;

import io.marklove.carinsurance.dto.NotificationDetail;
import io.marklove.carinsurance.dto.NotificationList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    Page<NotificationList> getMyPageNotifications(Pageable pageable);

    Page<NotificationList> getCompanyNotifications(Pageable pageable);

    NotificationDetail getNotificationDetail(long id);
}
