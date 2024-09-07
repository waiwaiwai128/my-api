package com.myapi.myapiinterface.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Autowired
    private MessageSenderService messageSenderService;

    @Async
    public void processTestAsync(String traceId, String testData) {
        // 模拟处理 testData
        try {
            // 模拟延迟
            System.out.println("start process");
            Thread.sleep(2000); // 2 seconds delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // 打印 testData
        System.out.println("Asynchronously processed testData: " + testData);
        messageSenderService.sendTraceData(traceId, testData);
    }
}
