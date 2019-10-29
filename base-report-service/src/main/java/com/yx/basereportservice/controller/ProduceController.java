package com.yx.basereportservice.controller;

import com.yx.basereportservice.amqp.Producer;
import com.yx.basereportservice.amqp.QueueMessageSender;
import com.yx.basereportservice.constant.QueueMessgeConst;
import com.yx.basereportservice.enums.MsgEnum;
import com.yx.basereportservice.enums.PlatformEnum;
import com.yx.basereportservice.enums.UserTypeEnum;
import com.yx.basereportservice.model.BaseReport;
import com.yx.basereportservice.model.QueueMessge;
import com.yx.basereportservice.model.TestReport;
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

        TestReport testReport = new TestReport();
        testReport.setUid(1003);
        testReport.setIp("123.23.0.1");
        testReport.setPlatform(PlatformEnum.IOS);
        testReport.setUuid("yx123456sdfgh");
        testReport.setTimestamp(new Date());
        testReport.setUserName("rrr");
        testReport.setDeviceId("PC端");
        testReport.setFinishTime(new Date());
        testReport.setDomain("werr");
        testReport.setIsFake("1");
        testReport.setParentId(23);
        testReport.setParentName("qqq");
        testReport.setRegDays(30L);
        testReport.setRegMonths(1l);
        testReport.setRegTime(new Date());
        testReport.setRegWeeks(4l);
        testReport.setUserType(UserTypeEnum.MEMBER);
        testReport.setName("你哈");

        log.info("===>>上报时间：{}",System.currentTimeMillis());

        producer.report(MsgEnum.Recharge,testReport);

        return "ok";
    }
}
