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
        // Traitez le message ici
        //String[] ids = message.split(",");
        try {
            System.out.println(message + " good");
            /*
            Long clientId = Long.parseLong(ids[0]);


            System.out.println(clientId);
            System.out.println("*****************");
            Long orderId = Long.parseLong(ids[1]);
            // Vous pouvez maintenant utiliser clientId et orderId comme vous le souhaitez
            System.out.println("ClientId: " + clientId + ", OrderId: " + orderId);*/
        } catch (NumberFormatException e) {
            // Gérer l'erreur de parsing
            System.err.println("Erreur de parsing des IDs : " + e.getMessage());
        }
    }

    public String getReceivedMessage() {
        return this.receivedMessage;
    }
}


