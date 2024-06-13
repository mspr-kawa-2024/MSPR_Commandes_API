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
    private String receivedMessageForClientIds;
    private String receivedMessageForProductIds;

    @RabbitListener(queues = "productToSendQueue")
    public void receiveProductOfOrder(String message) {
        this.receivedMessage = message;
    }

    public String getReceivedMessage() {
        return this.receivedMessage;
    }

    @RabbitListener(queues = "responseClientIdsVerificationQueue")
    public void receiveResponseForClientIdsVerification(String message) {
        this.receivedMessageForClientIds = message;
    }

    public String getReceivedMessageForClientIds() {
        return this.receivedMessageForClientIds;
    }

    @RabbitListener(queues = "responseProductIdsVerificationQueue")
    public void responseProductIdsVerificationQueue(String message) {
        this.receivedMessageForProductIds = message;
    }

    public String getReceivedMessageForProductIds() {
        return this.receivedMessageForProductIds;
    }
}


