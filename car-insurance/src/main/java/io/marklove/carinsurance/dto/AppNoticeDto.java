package io.marklove.carinsurance.dto;

import io.marklove.carinsurance.constant.enums.ENoticeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppNoticeDto {
    private long id;

    private String content;

    private boolean hasRead;

    private ENoticeType type;

    private Long detailId;

    private LocalDate createdOn;
}
