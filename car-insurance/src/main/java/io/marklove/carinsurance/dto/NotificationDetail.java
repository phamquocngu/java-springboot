package io.marklove.carinsurance.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationDetail {

    private String title;

    private String content;

    private LocalDateTime createdOn;
}
