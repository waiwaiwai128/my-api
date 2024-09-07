package com.myapi.backend.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "trace-exchange";
    public static final String QUEUE_NAME = "trace-queue";
    public static final String ROUTING_KEY = "trace-routing-key";

    @Bean
    public Queue traceQueue() {
        return new Queue(QUEUE_NAME, true); // true for durable
    }

    @Bean
    public DirectExchange traceExchange() {
        return new DirectExchange(EXCHANGE_NAME); // 使用 DirectExchange
    }

    @Bean
    public Binding binding(Queue traceQueue, DirectExchange traceExchange) {
        return BindingBuilder.bind(traceQueue).to(traceExchange).with(ROUTING_KEY);
    }
}
