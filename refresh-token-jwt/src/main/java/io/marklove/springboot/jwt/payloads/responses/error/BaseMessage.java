package io.marklove.springboot.jwt.payloads.responses.error;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ngupq
 */
@Getter
@Setter
public class BaseMessage {
    private String code;
    private String message;

    public  BaseMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
