package com.commandApi;

import com.commandApi.config.RabbitMQReceiver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RabbitMQReceiverTest {

    @InjectMocks
    private RabbitMQReceiver rabbitMQReceiver;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReceiveProductOfOrder() {
        String message = "product message";
        rabbitMQReceiver.receiveProductOfOrder(message);
        assertEquals(message, rabbitMQReceiver.getReceivedMessage());
    }

    @Test
    public void testReceiveResponseForClientIdsVerification() {
        String message = "client ids message";
        rabbitMQReceiver.receiveResponseForClientIdsVerification(message);
        assertEquals(message, rabbitMQReceiver.getReceivedMessageForClientIds());
    }

    @Test
    public void testResponseProductIdsVerificationQueue() {
        String message = "product ids message";
        rabbitMQReceiver.responseProductIdsVerificationQueue(message);
        assertEquals(message, rabbitMQReceiver.getReceivedMessageForProductIds());
    }
}
