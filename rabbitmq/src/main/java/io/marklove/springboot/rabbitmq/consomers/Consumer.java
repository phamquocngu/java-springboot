package io.marklove.springboot.rabbitmq.consomers;

import io.marklove.springboot.rabbitmq.model.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListeners({
            @RabbitListener(queues="${consumer.rabbitmq.queue1}", containerFactory="service1Factory"),
            @RabbitListener(queues="${consumer.rabbitmq.queue2}", containerFactory="service1Factory"),
            @RabbitListener(queues="${consumer.rabbitmq.queue3}", containerFactory="service1Factory")})
    public void recievedMessage(Log log) {
        System.out.println("Recieved Message: " + log);
    }
}
