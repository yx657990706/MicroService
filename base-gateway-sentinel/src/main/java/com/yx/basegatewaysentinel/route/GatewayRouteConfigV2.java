package com.yx.basegatewaysentinel.route;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosConfigProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @Author Jesse
 * @Date 2020/1/26 17:28
 * @Desc  结合nacos版的网关路由策略：路由规则从nocos中读取，规则自定义的JSON格式，字段均预先定义
 **/
@Slf4j
//@Component
public class GatewayRouteConfigV2 {

    //TODO  改成取配置
    private final static String dataId = "forehead-gateway-route-rule";

    @Autowired
    private NacosConfigProperties properties;

    private static JSONArray routeRule;

    @PostConstruct
    private void init() {
        try {
            ConfigService configService = NacosFactory.createConfigService(properties.getServerAddr());
            String source = configService.getConfig(dataId, properties.getGroup(), properties.getTimeout());
            if (StringUtils.isEmpty(source)) {
                return;
            }
            routeRule = JSONArray.parseArray(source);
        } catch (NacosException e) {
            log.error("CustomerRouteLocator.init() error", e);
        }
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routesBuilder = builder.routes();
        for (int i = 0, len = routeRule.size(); i < len; i++) {
            JSONObject ruleObj = routeRule.getJSONObject(i);
            routesBuilder.route(ruleObj.getString("id"), r -> r.path(ruleObj.getString("path")).uri(ruleObj.getString("uri")));
        }
        return routesBuilder.build();
    }
}
