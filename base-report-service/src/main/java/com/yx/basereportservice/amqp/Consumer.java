package com.yx.basereportservice.amqp;

import com.rabbitmq.client.Channel;
import com.yx.basereportservice.enums.MsgEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

@Slf4j
@Component
@RabbitListener(queues = "${report.queue}", containerFactory = "ReportListenerContainerFactory", admin = "ReportRabbitAdmin")
public class Consumer {

    private final ApplicationContext applicationContext;

    private static MsgEnum[] onlyEvents = {MsgEnum.Recharge, MsgEnum.Betting, MsgEnum.UpAmount, MsgEnum.AddCoin,MsgEnum.Withdraw};

    @Autowired
    public Consumer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @RabbitHandler
    public void process(HashMap<String, Object> data, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            log.info("evlog接收到消息:{}", data);
            //事件校验
            Integer eventId = (Integer) data.get("event");
            MsgEnum event = MsgEnum.valueOf(eventId);
            if (event == null || !Arrays.asList(onlyEvents).contains(event)) {
                channel.basicAck(tag, false);//手工消息确认
                return;
            }
            String eventKey = String.valueOf(eventId);
            if (!data.containsKey(eventKey)) {
                channel.basicAck(tag, false);
                return;
            }
            //参数处理
//            Long timestamp = (Long) data.get("timestamp");
//            EventModel eventModel = new EventModel();
//            eventModel.setUid((Integer) data.get("uid"));
//            eventModel.setTimestamp(new Date(timestamp));
//            eventModel.setUuid(data.get("uuid").toString());
//            eventModel.setMsg((HashMap<String, Object>) data.get(eventKey));
//            String eventName = event.name();
//            //getBean的方式获取处理实现类
//            EventHandle eventHandle = (EventHandle) applicationContext.getBean(eventName + "EventHandle");
//            eventHandle.process(eventModel);
            channel.basicAck(tag, false);
            log.info("evlog消息处理完成:{}", data);
        } catch(Throwable e) {
            boolean reQueue = true;
            if (e instanceof NumberFormatException) {
                reQueue = false;
            }
            try {
                channel.basicReject(tag, reQueue);//reject后进入死信队列
            } catch (IOException ex) {
                log.error("report event reject error", ex);
            }
            log.error("report event consume error", e);
        }
    }
}