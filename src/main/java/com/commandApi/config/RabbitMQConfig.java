package com.commandApi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_QUEUE_NAME = "orderQueue";
    public static final String ORDER_QUEUE_ID = "orderIdQueue";
    public static final String ORDER_PRODUCT_QUEUE_NAME = "orderProductQueue";
    public static final String RESPONSE_QUEUE_NAME = "responseQueue";

    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE_NAME, false);
    }

    @Bean
    public Queue orderIdQueue() {
        return new Queue(ORDER_QUEUE_ID, false);
    }

    @Bean
    public Queue orderProductQueue() {
        return new Queue(ORDER_PRODUCT_QUEUE_NAME, false);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(RESPONSE_QUEUE_NAME, false);
    }

    @Bean
    public Queue orderToSendQueue() {
        return new Queue("orderToSendQueue", false);
    }

    @Bean
    public Queue productsIdQueue() {
        return new Queue("productsIdQueue", false);
    }

    @Bean
    public Queue productToSendQueue() {
        return new Queue("productToSendQueue", false);
    }

    @Bean
    public Queue clientIdsIdQueue() {
        return new Queue("clientIdsIdQueue", false);
    }

    @Bean
    public Queue responseClientIdsVerificationQueue() {
        return new Queue("responseClientIdsVerificationQueue", false);
    }
    @Bean
    public Queue productIdsToProductQueue() {
        return new Queue("productIdsToProductQueue", false);
    }
    @Bean
    public Queue responseProductIdsVerificationQueue() {
        return new Queue("responseProductIdsVerificationQueue", false);
    }
}
