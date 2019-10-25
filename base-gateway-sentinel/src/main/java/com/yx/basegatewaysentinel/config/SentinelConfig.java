package com.yx.basegatewaysentinel.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.alibaba.nacos.NacosConfigProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @Author Jesse
 * @Date 2019/10/25 16:11
 **/
@Slf4j
@Component
public class SentinelConfig {
    @Autowired
    private NacosConfigProperties properties;

    @Value("${sentinel.system.rule.data-id}")
    private String systemRuleDataId;

    @Value("${sentinel.degrade.rule.data-id}")
    private String degradeRuleDataId;

    @Value("${sentinel.flow.rule.data-id}")
    private String flowRuleDataId;

    @PostConstruct
    public void init() {
        // 系统规则
        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new NacosDataSource<>(properties.getServerAddr(), properties.getGroup(), systemRuleDataId, source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {}));
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());
        // 降级规则
        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new NacosDataSource<>(properties.getServerAddr(), properties.getGroup(), degradeRuleDataId, source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {}));
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
        // 流控规则
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(properties.getServerAddr(), properties.getGroup(), degradeRuleDataId, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        // 自定义响应
//        WebCallbackManager.setUrlBlockHandler(new CustomUrlBlockHandler());
        // 增加监听
        try {
            ConfigService configService = NacosFactory.createConfigService(properties.getServerAddr());
            configService.addListener(systemRuleDataId, properties.getGroup(), new SystemRuleListener());
            configService.addListener(degradeRuleDataId, properties.getGroup(), new DegradeRuleListener());
            configService.addListener(flowRuleDataId, properties.getGroup(), new FlowRuleListener());
        } catch (NacosException e) {
            log.error("SentinelConfiguration.init()", e);
        }
    }

    class FlowRuleListener implements Listener {

        @Override
        public Executor getExecutor() {
            return null;
        }

        @Override
        public void receiveConfigInfo(String configInfo) {
            log.info("FlowRuleListener收到的内容是{}", configInfo);
        }

    }

    class DegradeRuleListener implements Listener {

        @Override
        public Executor getExecutor() {
            return null;
        }

        @Override
        public void receiveConfigInfo(String configInfo) {
            log.info("DegradeRuleListener收到的内容是{}", configInfo);
        }

    }

    class SystemRuleListener implements Listener {

        @Override
        public Executor getExecutor() {
            return null;
        }

        @Override
        public void receiveConfigInfo(String configInfo) {
            log.info("SystemRuleListener收到的内容是{}", configInfo);
        }

    }

//    class CustomUrlBlockHandler implements UrlBlockHandler {
//
//        @Override
//        public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
//            httpServletResponse.setContentType("application/json;charset=utf-8");
//            PrintWriter printWriter = httpServletResponse.getWriter();
//            printWriter.print(ResultGenerator.genFailResult("访问频率限制"));
//            printWriter.flush();
//            printWriter.close();
//        }
//
//    }
}
