package com.myapi.myapiinterface.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import static com.myapi.myapiinterface.config.RabbitMQConfig.EXCHANGE_NAME;
import static com.myapi.myapiinterface.config.RabbitMQConfig.ROUTING_KEY;

@Service
public class MessageSenderService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendTraceData(String traceId, String testData) {
        // 创建消息体
        String messageBody = String.format("{\"traceId\":\"%s\", \"testData\":\"%s\"}", traceId, testData);

        // 发送消息到 RabbitMQ，设置消息的过期时间为 30 分钟
        amqpTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, messageBody, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) {
                // 设置消息过期时间为 30 分钟 (1800000 毫秒)
                message.getMessageProperties().setExpiration("1800000");
                return message;
            }
        });
    }
}
