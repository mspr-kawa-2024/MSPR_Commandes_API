package com.commandApi.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String receivedMessage;

    @RabbitListener(queues = "productToSendQueue")
    public void receiveProductOfOrder(String message) {
        this.receivedMessage = message;
        System.out.println(message + " good");
    }

    public String getReceivedMessage() {
        return this.receivedMessage;
    }
}


