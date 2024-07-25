package com.myapi.myapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.myapi.myapigateway", "com.myapi.myapiclientsdk"})
public class MyApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApiGatewayApplication.class, args);
    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("tobaidu", r -> r.path("/baidu")
//                        .uri("http://www.baidu.com"))
//                .route("towaiwaiwai", r -> r.path("/yupiicu")
//                        .uri("https://gptcat.top/GPT-4"))
//                .build();
//    }

}
