package com.commandApi.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service RabbitMQReceiver
 *
 * Cette classe est un service Spring chargé de recevoir et traiter les messages
 * provenant de différentes files RabbitMQ. Elle utilise RabbitTemplate pour
 * interagir avec RabbitMQ et RabbitListener pour écouter les messages des files
 * spécifiées.
 *
 * - receiveProductOfOrder : écoute la file "productToSendQueue" et stocke le message reçu.
 * - receiveResponseForClientIdsVerification : écoute la file "responseClientIdsVerificationQueue" et stocke le message reçu.
 * - responseProductIdsVerificationQueue : écoute la file "responseProductIdsVerificationQueue" et stocke le message reçu.
 *
 * Les méthodes getter permettent de récupérer les messages reçus pour un traitement ultérieur.
 */
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


