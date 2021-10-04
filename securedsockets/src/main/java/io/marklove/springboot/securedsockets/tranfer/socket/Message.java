package io.marklove.springboot.securedsockets.tranfer.socket;

import lombok.Data;

@Data
public class Message {
    private String from;
    private String to;
    private String text;
}
