package com.commandApi.config;

import com.commandApi.command.Command;
import com.commandApi.command.CommandService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrderToClient(String order) {
        rabbitTemplate.convertAndSend("orderToSendQueue", order);
    }

    public void sendOrderToProduct(String orderId) {
        rabbitTemplate.convertAndSend("orderToSendQueue", orderId);
    }

    public void sendProductsIdToProduct(String productsId) {
        rabbitTemplate.convertAndSend("productsIdQueue", productsId);
    }
}

