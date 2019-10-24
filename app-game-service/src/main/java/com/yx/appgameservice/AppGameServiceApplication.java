package com.yx.appgameservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AppGameServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppGameServiceApplication.class, args);
    }

}
