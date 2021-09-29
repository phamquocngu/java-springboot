package io.marklove.springboot.rabbitmq.producers;

import io.marklove.springboot.rabbitmq.model.Log;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProducerLog {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${producer.rabbitmq.exchange}")
    private String exchange;

    public void produce(Log logs){
        String routingKey = logs.getRoutingKey();
        amqpTemplate.convertAndSend(exchange, routingKey, logs);
        System.out.println("Send msg = " + logs);
    }
}
