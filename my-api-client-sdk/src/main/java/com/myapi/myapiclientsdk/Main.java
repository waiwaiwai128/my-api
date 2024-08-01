package com.myapi.myapiclientsdk;

import com.myapi.myapiclientsdk.client.MyApiClient;
import com.myapi.myapiclientsdk.model.User;

public class Main {
    public static void main(String[] args) {
        MyApiClient myApiClient = new MyApiClient("77305ba97efa83706191dabbc08d09cf", "a8934b918f82e415b4d2a5167964c29b");
//        String result1 = myApiClient.getNamebyGet("cdd1");
//        String result2 = myApiClient.getNamebyPost("cdd2");
        User user = new User();
        user.setUsername("cdd3");
        String result3 = myApiClient.getUserNamebyPost(user);
//        System.out.println(result1);
//        System.out.println(result2);
        System.out.println(result3);
    }
}

