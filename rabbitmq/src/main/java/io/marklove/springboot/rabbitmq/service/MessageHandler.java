package io.marklove.springboot.rabbitmq.service;

import io.marklove.springboot.rabbitmq.model.Message;

public interface MessageHandler {
    void handleIncomingMessage(Message msg);
}
