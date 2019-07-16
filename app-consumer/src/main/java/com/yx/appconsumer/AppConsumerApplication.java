package com.yx.appconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@EnableDiscoveryClient//支持各种注册中心，@EnableEurekaClient支持eureka
@SpringBootApplication
public class AppConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppConsumerApplication.class, args);
    }

    @LoadBalanced //开启客户端负载均衡
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
