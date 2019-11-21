package com.yx.basegatewaysentinel.route;

import com.yx.basegatewaysentinel.filter.TestTokenFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @Author Jesse
 * @Date 2019/10/23 18:40
 **/
@Component
public class GatewayRouteConfig {
    /**
     *  路由配置<br>
     *
     * 配置了一个 id 为 host_route的路由，当访问地址http://localhost:8080/a/about时会自动转发到地址：http://localhost:2006/about
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        //可以配置多种路由,stripPrefix移除路径的第一段(即移除下面的/a或/b)
        return builder.routes()
                //自定义GatewayFilter
//                .route("host_route04", r -> r.path("/c/**").filters(f -> f.stripPrefix(1)).uri("lb://app-web-service").filters(new TestTokenFilter()))
                //服务转发
                .route("host_route02", r -> r.path("/a/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:2006"))
                .route("host_route03", r -> r.path("/b/**").filters(f -> f.stripPrefix(1)).uri("http://localhost:8902"))
                //使用服务名的方式转发
                .route("host_route04", r -> r.path("/c/**").filters(f -> f.stripPrefix(1)).uri("lb://app-game-service"))
                .build();
    }

    /**
     * 基于路径的路由断言匹配
     * @return
     */
    @Bean
    public RouterFunction<ServerResponse> testFunRouterFunction() {
        //访问http://localhost:2006/countTest/** 均会返回hello123
        RouterFunction<ServerResponse> route = RouterFunctions.route(
                RequestPredicates.path("/countTest"),
                request -> ServerResponse.ok().body(BodyInserters.fromObject("hello123")));
        return route;
    }


}
