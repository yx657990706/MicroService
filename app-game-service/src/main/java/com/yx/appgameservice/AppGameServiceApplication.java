package com.yx.appgameservice;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@NacosConfigurationProperties(dataId = "app-game-service-dev.properties")
public class AppGameServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppGameServiceApplication.class, args);
    }

}
