package com.commandApi.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "orderProductQueue")
    public Object receiveClientIdAndOrderId() {
        Object response = rabbitTemplate.receive("orderProductQueue");
        System.out.println("Received message: " + response);
        // Process the message as needed
        return response;
    }
}

