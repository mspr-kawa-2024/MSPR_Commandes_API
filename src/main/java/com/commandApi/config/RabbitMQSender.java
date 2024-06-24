package com.commandApi.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrderToClient(String order) {
        rabbitTemplate.convertAndSend("orderToSendQueue", order);
    }

    public void sendProductsIdToProduct(String productsId) {
        rabbitTemplate.convertAndSend("productsIdQueue", productsId);
    }

    public void sendClientIdsToClient(String clientTds) {
        rabbitTemplate.convertAndSend("clientIdsIdQueue", clientTds);
    }

    public void sendProductIdsToProduct(String productsTds) {
        rabbitTemplate.convertAndSend("productIdsToProductQueue", productsTds);
    }
}

