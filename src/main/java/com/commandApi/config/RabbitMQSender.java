package com.commandApi.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service RabbitMQSender
 *
 * Cette classe est un service Spring responsable de l'envoi de messages vers différentes
 * files RabbitMQ. Elle utilise RabbitTemplate pour envoyer les messages vers les files spécifiées.
 *
 * - sendOrderToClient : Envoie une commande vers la file "orderToSendQueue".
 * - sendProductsIdToProduct : Envoie des identifiants de produits vers la file "productsIdQueue".
 * - sendClientIdsToClient : Envoie des identifiants de clients vers la file "clientIdsIdQueue".
 * - sendProductIdsToProduct : Envoie des identifiants de produits vers la file "productIdsToProductQueue".
 */
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

