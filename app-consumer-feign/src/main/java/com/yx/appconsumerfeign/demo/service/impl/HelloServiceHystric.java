package com.yx.appconsumerfeign.demo.service.impl;

import com.yx.appconsumerfeign.demo.service.HelloService;
import com.yx.appcore.model.Book;
import org.springframework.stereotype.Component;

/**
 * @author jesse
 * @version v1.0
 * @project MicroService
 * @encoding UTF-8
 * @time 2019/7/15 8:32 PM
 * @Description
 */
@Component
public class HelloServiceHystric implements HelloService {
    @Override
    public String hello() {
        return "sorry，unable service 1";
    }

    @Override
    public String hello(String name) {
        return "sorry，unable service 2";
    }

    @Override
    public Book hello(String name, String author, Integer price) {
        return null;
    }

    @Override
    public String hello(Book book) {
        return "sorry，unable service 3";
    }
}
