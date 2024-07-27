package com.myapi.myapigateway;


import com.myapi.backend.provider.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.myapi.myapigateway", "com.myapi.myapiclientsdk"})
@EnableDubbo
public class MyApiGatewayApplication {
    @DubboReference
    private DemoService demoService;

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(MyApiGatewayApplication.class, args);
        MyApiGatewayApplication application = context.getBean(MyApiGatewayApplication.class);
        String result = application.doSayHello("world");
        String result2 = application.doSayHello2("world");
        System.out.println("result: " + result);
        System.out.println("result: " + result2);
    }

    public String doSayHello(String name) {
        return demoService.sayHello(name);
    }

    public String doSayHello2(String name) {
        return demoService.sayHello2(name);
    }
}
