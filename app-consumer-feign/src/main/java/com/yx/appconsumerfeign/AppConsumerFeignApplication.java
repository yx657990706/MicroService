package com.yx.appconsumerfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient//支持各种注册中心，@EnableEurekaClient支持eureka
@EnableFeignClients
@SpringBootApplication
public class AppConsumerFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppConsumerFeignApplication.class, args);
    }
}
