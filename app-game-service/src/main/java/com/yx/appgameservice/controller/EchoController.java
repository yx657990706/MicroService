package com.yx.appgameservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Jesse
 * @Date 2019/10/24 15:02
 **/
@RestController
public class EchoController {


    @Value("${uuu}")
    private String str;

    @Value("${mysqlURL}")
    private String mysqlURL;

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string+str;
    }

    @RequestMapping(value = "/echo2", method = RequestMethod.GET)
    public String echo2() {
        return "Hello Nacos config " + mysqlURL;
    }
}
