package com.commandApi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RabbitMQConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testOrderQueue() {
        Queue orderQueue = applicationContext.getBean("orderQueue", Queue.class);
        assertNotNull(orderQueue);
        assertEquals("orderQueue", orderQueue.getName());
    }

    @Test
    public void testOrderIdQueue() {
        Queue orderIdQueue = applicationContext.getBean("orderIdQueue", Queue.class);
        assertNotNull(orderIdQueue);
        assertEquals("orderIdQueue", orderIdQueue.getName());
    }

    @Test
    public void testOrderProductQueue() {
        Queue orderProductQueue = applicationContext.getBean("orderProductQueue", Queue.class);
        assertNotNull(orderProductQueue);
        assertEquals("orderProductQueue", orderProductQueue.getName());
    }

    @Test
    public void testResponseQueue() {
        Queue responseQueue = applicationContext.getBean("responseQueue", Queue.class);
        assertNotNull(responseQueue);
        assertEquals("responseQueue", responseQueue.getName());
    }

    @Test
    public void testOrderToSendQueue() {
        Queue orderToSendQueue = applicationContext.getBean("orderToSendQueue", Queue.class);
        assertNotNull(orderToSendQueue);
        assertEquals("orderToSendQueue", orderToSendQueue.getName());
    }

    @Test
    public void testProductsIdQueue() {
        Queue productsIdQueue = applicationContext.getBean("productsIdQueue", Queue.class);
        assertNotNull(productsIdQueue);
        assertEquals("productsIdQueue", productsIdQueue.getName());
    }

    @Test
    public void testProductToSendQueue() {
        Queue productToSendQueue = applicationContext.getBean("productToSendQueue", Queue.class);
        assertNotNull(productToSendQueue);
        assertEquals("productToSendQueue", productToSendQueue.getName());
    }

    @Test
    public void testClientIdsIdQueue() {
        Queue clientIdsIdQueue = applicationContext.getBean("clientIdsIdQueue", Queue.class);
        assertNotNull(clientIdsIdQueue);
        assertEquals("clientIdsIdQueue", clientIdsIdQueue.getName());
    }

    @Test
    public void testResponseClientIdsVerificationQueue() {
        Queue responseClientIdsVerificationQueue = applicationContext.getBean("responseClientIdsVerificationQueue", Queue.class);
        assertNotNull(responseClientIdsVerificationQueue);
        assertEquals("responseClientIdsVerificationQueue", responseClientIdsVerificationQueue.getName());
    }

    @Test
    public void testProductIdsToProductQueue() {
        Queue productIdsToProductQueue = applicationContext.getBean("productIdsToProductQueue", Queue.class);
        assertNotNull(productIdsToProductQueue);
        assertEquals("productIdsToProductQueue", productIdsToProductQueue.getName());
    }

    @Test
    public void testResponseProductIdsVerificationQueue() {
        Queue responseProductIdsVerificationQueue = applicationContext.getBean("responseProductIdsVerificationQueue", Queue.class);
        assertNotNull(responseProductIdsVerificationQueue);
        assertEquals("responseProductIdsVerificationQueue", responseProductIdsVerificationQueue.getName());
    }
}
