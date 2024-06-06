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

    public void sendProductsInOrder(Command command) {
        //String orderDetails = commandService.sendProducts();
        rabbitTemplate.convertAndSend(RabbitMQConfig.RESPONSE_QUEUE_NAME, command);
    }

    public void sendToOrderProductQueue(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_PRODUCT_QUEUE_NAME, message);
    }
}
