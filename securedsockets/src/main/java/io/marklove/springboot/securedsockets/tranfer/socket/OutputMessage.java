package io.marklove.springboot.securedsockets.tranfer.socket;

import lombok.Data;

@Data
public class OutputMessage extends Message {

    private String time;

    public OutputMessage(final String from, final String text, final String time) {
        setFrom(from);
        setText(text);
        this.time = time;
    }
}
