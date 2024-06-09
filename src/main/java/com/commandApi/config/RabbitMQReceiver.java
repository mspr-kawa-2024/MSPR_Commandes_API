package com.commandApi.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver {

    private String receivedMessage;

    @RabbitListener(queues = "orderProductQueue")
    public void receiveMessage(String message) {
        this.receivedMessage = message;
        try {
        } catch (NumberFormatException e) {
        }
    }

    public String getReceivedMessage() {
        return this.receivedMessage;
    }
}


