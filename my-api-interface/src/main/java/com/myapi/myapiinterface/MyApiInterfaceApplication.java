package com.myapi.myapiinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.myapi.myapiinterface", "com.myapi.myapiclientsdk"})
public class MyApiInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApiInterfaceApplication.class, args);
    }

}
