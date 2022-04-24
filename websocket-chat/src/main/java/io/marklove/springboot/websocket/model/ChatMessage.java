package io.marklove.springboot.websocket.model;

import io.marklove.springboot.websocket.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
}
