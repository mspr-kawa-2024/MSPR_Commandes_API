package com.commandApi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_QUEUE_NAME = "orderQueue";
    public static final String ORDER_PRODUCT_QUEUE_NAME = "orderProductQueue";
    public static final String RESPONSE_QUEUE_NAME = "responseQueue";

    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE_NAME, false);
    }

    @Bean
    public Queue orderProductQueue() {
        return new Queue(ORDER_PRODUCT_QUEUE_NAME, false);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(RESPONSE_QUEUE_NAME, false);
    }
}
