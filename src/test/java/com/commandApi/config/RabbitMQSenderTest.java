package com.commandApi.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

public class RabbitMQSenderTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMQSender rabbitMQSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendOrderToClient() {
        String order = "Sample product JSON";

        rabbitMQSender.sendOrderToClient(order);

        verify(rabbitTemplate, times(1)).convertAndSend("orderToSendQueue", order);
    }

    @Test
    public void testSendProductsIdToProduct() {
        String productsId = "Sample response JSON";

        rabbitMQSender.sendProductsIdToProduct(productsId);

        verify(rabbitTemplate, times(1)).convertAndSend("productsIdQueue", productsId);
    }

    @Test
    public void testSendClientIdsToClient() {
        String clientTds = "Sample response JSON";

        rabbitMQSender.sendClientIdsToClient(clientTds);

        verify(rabbitTemplate, times(1)).convertAndSend("clientIdsIdQueue", clientTds);
    }

    @Test
    public void testSendProductIdsToProduct() {
        String productsTds = "Sample response JSON";

        rabbitMQSender.sendProductIdsToProduct(productsTds);

        verify(rabbitTemplate, times(1)).convertAndSend("productIdsToProductQueue", productsTds);
    }
}
