package com.yx.appwebservice.service;

import com.yx.appwebservice.service.hystric.HelloFeignServiceHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用feign调用RPC服务
 * //configuration = xxx.class  这个类配置Hystrix的一些精确属性,例如服务超时
 * //value=“你用到的服务名称”
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

    /**
     * 多参数需要指定映射的属性
     * @param name
     * @param password
     * @return
     */
//    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    @GetMapping(value = "/hello2")
    String hello2(@RequestParam("name") String name,@RequestParam("password") String password);

}
