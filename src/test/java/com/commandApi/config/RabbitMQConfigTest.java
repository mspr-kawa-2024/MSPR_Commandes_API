package com.commandApi.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RabbitMQConfigTest {

    private final RabbitMQConfig rabbitMQConfig = new RabbitMQConfig();

    @Test
    public void testProductsIdQueue() {
        Queue queue = rabbitMQConfig.productsIdQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("productsIdQueue", queue.getName(), "Queue name should be 'productsIdQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    @Test
    public void testProductToSendQueue() {
        Queue queue = rabbitMQConfig.productToSendQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("productToSendQueue", queue.getName(), "Queue name should be 'productToSendQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    @Test
    public void testOrderQueue() {
        Queue queue = rabbitMQConfig.orderQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("orderQueue", queue.getName(), "Queue name should be 'orderQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    @Test
    public void testResponseProductIdsVerificationQueue() {
        Queue queue = rabbitMQConfig.responseProductIdsVerificationQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("responseProductIdsVerificationQueue", queue.getName(), "Queue name should be 'responseProductIdsVerificationQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    // Additional tests for the remaining queues
    @Test
    public void testOrderIdQueue() {
        Queue queue = rabbitMQConfig.orderIdQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("orderIdQueue", queue.getName(), "Queue name should be 'orderIdQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    @Test
    public void testOrderProductQueue() {
        Queue queue = rabbitMQConfig.orderProductQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("orderProductQueue", queue.getName(), "Queue name should be 'orderProductQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    @Test
    public void testResponseQueue() {
        Queue queue = rabbitMQConfig.responseQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("responseQueue", queue.getName(), "Queue name should be 'responseQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    @Test
    public void testOrderToSendQueue() {
        Queue queue = rabbitMQConfig.orderToSendQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("orderToSendQueue", queue.getName(), "Queue name should be 'orderToSendQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    @Test
    public void testClientIdsIdQueue() {
        Queue queue = rabbitMQConfig.clientIdsIdQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("clientIdsIdQueue", queue.getName(), "Queue name should be 'clientIdsIdQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    @Test
    public void testResponseClientIdsVerificationQueue() {
        Queue queue = rabbitMQConfig.responseClientIdsVerificationQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("responseClientIdsVerificationQueue", queue.getName(), "Queue name should be 'responseClientIdsVerificationQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }

    @Test
    public void testProductIdsToProductQueue() {
        Queue queue = rabbitMQConfig.productIdsToProductQueue();
        assertNotNull(queue, "Queue should not be null");
        assertEquals("productIdsToProductQueue", queue.getName(), "Queue name should be 'productIdsToProductQueue'");
        assertEquals(false, queue.isDurable(), "Queue should not be durable");
    }
}
