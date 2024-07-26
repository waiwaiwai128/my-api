package com.myapi.myapiclientsdk;

import com.myapi.myapiclientsdk.client.MyApiClient;
import com.myapi.myapiclientsdk.model.User;

public class Main {
    public static void main(String[] args) {
        MyApiClient myApiClient = new MyApiClient("ak", "sk");
        String result1 = myApiClient.getNamebyGet("鱼皮");
        String result2 = myApiClient.getNamebyPost("鱼皮");
        User user = new User();
        user.setUsername("鲤鱼旗");
        String result3 = myApiClient.getUserNamebyPost(user);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }
}

