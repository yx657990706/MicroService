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
        return builder.routes()
                .route(r ->
                        r.path("/java/**")
                                .filters(
                                        f -> f.stripPrefix(1)
                                )
                                .uri("http://localhost:8090/helloWorld")
                )
                .build();
    }
}
