package com.myapi.backend.service.impl;

import com.myapi.backend.config.RabbitMQConfig;
import com.myapi.backend.service.TraceDataService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TraceDataServiceImpl implements TraceDataService {
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    @Override
    public void receiveMessage(String message) {
        System.out.println("Received message from RabbitMQ: " + message);
        // 解析消息内容并处理
        // 例如，解析 traceId 和 testData
        // 根据需要做进一步处理
    }
}
