package com.yx.basegatewaysentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Jesse
 * @Date 2019/10/23 15:46
 **/
@Slf4j
@RestController
@RequestMapping("/count")
public class TestSentinelController {

    //原子计数器
    private static AtomicInteger ai = new AtomicInteger(1);

    @GetMapping(value = "/getCount")
    @SentinelResource("getCount")
    public Integer countQuery() {

        int count = ai.getAndIncrement();
        log.info("===>>查询开始：{}   当前第{}次", System.currentTimeMillis(), count);
        return count;
    }
}
