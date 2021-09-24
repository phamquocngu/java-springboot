package io.marklove.springboot.firebase.dto;

import lombok.Data;

/**
 * @author ngupq
 */
@Data
public class NotificationRequestDto {

    private String target;
    private String title;
    private String body;
}
