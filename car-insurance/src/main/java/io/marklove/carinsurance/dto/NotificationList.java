package io.marklove.carinsurance.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationList {
    private long id;

    private String title;

    private String content;

    private LocalDateTime createdOn;
}
