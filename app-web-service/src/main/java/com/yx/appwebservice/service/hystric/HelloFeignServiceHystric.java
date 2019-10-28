package com.yx.appwebservice.service.hystric;

import com.yx.appwebservice.service.HelloFeignService;
import org.springframework.stereotype.Component;


/**
 * 客户端的feign的服务降级响应
 */
@Component
public class HelloFeignServiceHystric implements HelloFeignService {

    @Override
    public String hello() {
        return "sorry，unable service 1";
    }

    @Override
    public String hello(String name) {
        return "sorry，unable service 2";
    }

    /**
     * 多参数需要指定映射的属性
     *
     * @param name
     * @param password
     * @return
     */
    @Override
    public String hello2(String name, String password) {
        return "sorry，unable service 3";
    }

}
