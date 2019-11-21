package com.yx.basegatewaysentinel.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义GatewayFilter(只对指定的路由起作用)
 */
public class TestTokenFilter implements GatewayFilter, Ordered {

    private static final String AUTHORIZE_TOKEN = "token";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpResponse response = exchange.getResponse();

        //TODO 部分URL放行处理（取配置文件，假设规则为某种路径开头的放行）
        boolean skip = false;
        if(skip){
            return chain.filter(exchange);
        }

        //获取请求头中的token信息
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(AUTHORIZE_TOKEN);
        //token校验
        if (StringUtils.isBlank(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //TODO 解析token
        String uid = "123";

        //TODO redis查询authToken
        String authToken = "QWER";
        if (StringUtils.isBlank(authToken)||!token.endsWith(authToken)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 6;
    }
}
