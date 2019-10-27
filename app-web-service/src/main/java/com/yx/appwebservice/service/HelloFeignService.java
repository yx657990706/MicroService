package com.yx.appwebservice.service;

import com.yx.appwebservice.service.hystric.HelloFeignServiceHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用feign调用RPC服务
 */
@FeignClient(value = "app-game-service",fallback= HelloFeignServiceHystric.class)//@FeignClient注解来指定服务名进而绑定服务
@Service
public interface HelloFeignService {

    /**
     * 实际上调用的是app-game-service服务的/hello方法
     * @return
     */
    @RequestMapping("/hello")
    String hello();

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

}
