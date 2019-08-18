package com.yx.apigatewayserver.conf;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jesse
 * @version v1.0
 * @project MicroService
 * @encoding UTF-8
 * @time 2019/7/16 10:50 PM
 * @Description
 */
@Configuration
public class GatewayRoutesConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        //“/get”请求都转发到“http://httpbin.org/get”。在route配置上，添加了一个filter，该filter会将请求添加一个header,key为hello，value为world
        return builder.routes()
                .route(r ->
                                r.path("/get")
                                        .filters(
                                                f -> f.addRequestHeader("Hello", "World")
                                        )
//                                .uri("http://localhost:8090/helloWorld")
                                        .uri("http://httpbin.org:80")
                )
                .build();
    }
}
