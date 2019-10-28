package com.yx.basereportservice.controller;

import com.alibaba.fastjson.JSON;
import com.yx.basereportservice.amqp.Producer;
import com.yx.basereportservice.amqp.QueueMessageSender;
import com.yx.basereportservice.constant.QueueMessgeConst;
import com.yx.basereportservice.enums.MsgEnum;
import com.yx.basereportservice.enums.PlatformEnum;
import com.yx.basereportservice.enums.UserTypeEnum;
import com.yx.basereportservice.model.BaseReport;
import com.yx.basereportservice.model.QueueMessge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class ProduceController {

    @Autowired
    private Producer producer;

    @Autowired
    private QueueMessageSender sender;

    @GetMapping(value = "/addMQData2")
    public String addTestData2(){

        String bank = "{\"name\":\"zhangsna\",\"pwd\":\"123456\"}";
        //队列消息封装
        QueueMessge queueMessge = new QueueMessge();
        queueMessge.put(QueueMessgeConst.BEAN_NAME_KEY, "helloBean");
        queueMessge.put(QueueMessgeConst.OPENID_KEY, "yyyy0001");
        queueMessge.put(QueueMessgeConst.BIZ_JSON_STRING_KEY, bank);

        //发送消息到rabbitMQ队列
        final boolean b = sender.convertAndSend("yx-report-queue", queueMessge);
        log.info("队列推送结果===>>{}", b);
        return "ok";
    }
    @GetMapping(value = "/addMQData")
    public String addTestData(){

        BaseReport baseReport = new BaseReport();
        baseReport.setPlatform(PlatformEnum.IOS);
        baseReport.setUid(1003);
        baseReport.setUuid("yx123456sdfgh");
        baseReport.setTimestamp(new Date());
        baseReport.setUserName("rrr");
        baseReport.setDeviceId("PC端");
        baseReport.setIp("123.23.0.1");
        baseReport.setFinishTime(new Date());
        baseReport.setDomain("werr");
        baseReport.setIsFake("1");
        baseReport.setParentId(23);
        baseReport.setParentName("qqq");
        baseReport.setRegDays(30L);
        baseReport.setRegMonths(1l);
        baseReport.setRegTime(new Date());
        baseReport.setRegWeeks(4l);
        baseReport.setUserType(UserTypeEnum.MEMBER);

        log.info("===>>上报时间：{}",System.currentTimeMillis());

        producer.report(MsgEnum.Recharge,baseReport);

        return "ok";
    }
}
