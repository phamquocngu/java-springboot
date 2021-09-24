package io.marklove.springboot.firebase.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ngupq
 */
@Data
public class SubscriptionRequestDto {

    String topicName;
    List<String> tokens;
}
