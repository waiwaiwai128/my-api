package com.myapi.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.myapi.backend.mapper")
@SpringBootApplication(scanBasePackages = {"com.myapi.myapiclientsdk", "com.myapi.backend"})
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

}
