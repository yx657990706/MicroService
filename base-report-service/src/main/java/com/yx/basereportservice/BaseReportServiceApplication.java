package com.yx.basereportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BaseReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseReportServiceApplication.class, args);
    }

}
