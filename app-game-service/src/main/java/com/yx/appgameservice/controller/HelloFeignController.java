package com.yx.appgameservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


/**
 *
 */
@Slf4j
@RestController
public class HelloFeignController {

    @Autowired
    private DiscoveryClient client;

//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @GetMapping(value = "/hello")
    public String index() throws Exception {
        List<ServiceInstance> instances = client.getInstances("app-game-service");
        for (int i = 0; i < instances.size(); i++) {
            log.info("/hello,host:" + instances.get(i).getHost() + ",service_id:" + instances.get(i).getServiceId());
        }
        return "Hello World";
    }

//    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    @GetMapping(value = "/hello1")
    public String hello1(@RequestParam String name) {
        return "hello " + name + "!";
    }

    @GetMapping(value = "/hello2")
    public String hello2(@RequestParam String name,@RequestParam String password) {
        return "hello " + name + "! 你的密码是："+password;
    }
}