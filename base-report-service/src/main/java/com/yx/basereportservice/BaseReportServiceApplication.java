package com.yx.basereportservice;

import com.yx.basecoreservice.utils.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class BaseReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseReportServiceApplication.class, args);
    }

    /**
     * 初始化上下文工具类
     * @return
     */
    @Bean
    public SpringUtils getSpringUtils(){
        return new SpringUtils();
    }

}
