package com.myapi.myapiinterface;

import com.myapi.myapiclientsdk.client.MyApiClient;
import com.myapi.myapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

// 表示这是一个基于Spring Boot的测试类
@SpringBootTest
class YuapiInterfaceApplicationTests {
    // 注入一个名为yuApiClient的Bean
    @Autowired
    private MyApiClient myApiClient;
    // 表示这是一个测试方法
    @Test
    void contextLoads() {
        // 调用yuApiClient的getNameByGet方法，并传入参数"yupi"，将返回的结果赋值给result变量
        String result = myApiClient.getNamebyGet("waiwaiwai1");

        String result1 = myApiClient.getNamebyPost("waiwaiwai2");
        // 创建一个User对象
        User user = new User();
        // 设置User对象的username属性为"陈东东"
        user.setUsername("waiwaiwai3");
        // 调用yuApiClient的getUserNameByPost方法，并传入user对象作为参数，将返回的结果赋值给usernameByPost变量
        String usernameByPost = myApiClient.getUserNamebyPost(user);
        // 打印result变量的值
        System.out.println(result);
        System.out.println(result1);
        // 打印usernameByPost变量的值
        System.out.println(usernameByPost);
    }

}

