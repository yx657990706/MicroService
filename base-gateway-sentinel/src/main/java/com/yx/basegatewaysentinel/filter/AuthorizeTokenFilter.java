package com.yx.basegatewaysentinel.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * token全局过滤器
 */
//@Order(value = 5)
//@Component
public class AuthorizeTokenFilter implements GlobalFilter {

    private static final String AUTHORIZE_TOKEN = "token";
    /**
     * 排除签名验证的API
     */
    private static List<String> SIGN_EXCLUDE;
    /**
     * 路径匹配验证器
     */
    private static PathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求的路径
        String requestPath = request.getURI().getRawPath();
        //TODO 部分URL放行处理（取配置文件，获取放行路径list，假设规则为某种路径开头的放行）
//        SIGN_EXCLUDE = null;//放行API
        boolean skip = this.checkSkip(requestPath);
        if(skip){
            return chain.filter(exchange);
        }
        //获取请求头中的token信息

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

    private boolean checkSkip(final String requestPath) {
        if (SIGN_EXCLUDE.contains(requestPath)) {
            return true;
        }
        for (String excludePath : SIGN_EXCLUDE) {
            if (PATH_MATCHER.match(excludePath, requestPath)) {
                return true;
            }
        }
        return false;
    }
}
