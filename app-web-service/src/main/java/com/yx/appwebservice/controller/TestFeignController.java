package com.yx.appwebservice.controller;

import com.yx.appwebservice.service.HelloFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestFeignController {
    @Autowired
    HelloFeignService helloFeignService;

    @RequestMapping("/hello")
    public String hello() {
        return helloFeignService.hello();
    }

    @RequestMapping("/hello1/a")
    public String hello1() {
        return "hello1/a -----";
    }
}
