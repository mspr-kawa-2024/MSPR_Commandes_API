package com.commandApi;

import com.commandApi.config.RabbitMQSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RabbitMQSenderTest {

    @InjectMocks
    private RabbitMQSender rabbitMQSender;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendOrderToClient() {
        String order = "order";
        rabbitMQSender.sendOrderToClient(order);
        verify(rabbitTemplate, times(1)).convertAndSend("orderToSendQueue", order);
    }

    @Test
    public void testSendOrderToProduct() {
        String orderId = "orderId";
        rabbitMQSender.sendOrderToProduct(orderId);
        verify(rabbitTemplate, times(1)).convertAndSend("orderToSendQueue", orderId);
    }

    @Test
    public void testSendProductsIdToProduct() {
        String productsId = "productsId";
        rabbitMQSender.sendProductsIdToProduct(productsId);
        verify(rabbitTemplate, times(1)).convertAndSend("productsIdQueue", productsId);
    }

    @Test
    public void testSendClientIdsToClient() {
        String clientTds = "clientTds";
        rabbitMQSender.sendClientIdsToClient(clientTds);
        verify(rabbitTemplate, times(1)).convertAndSend("clientIdsIdQueue", clientTds);
    }

    @Test
    public void testSendProductIdsToProduct() {
        String productsTds = "productsTds";
        rabbitMQSender.sendProductIdsToProduct(productsTds);
        verify(rabbitTemplate, times(1)).convertAndSend("productIdsToProductQueue", productsTds);
    }
}
