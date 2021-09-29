package io.marklove.springboot.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    private String content;

    private String routingKey;

    @Override
    public String toString() {
        return String.format("{content = %s, routingKey = %s}", content, routingKey);
    }
}
