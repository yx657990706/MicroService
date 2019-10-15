package com.yx.basereportservice.amqp;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Component
@RabbitListener(queues = "${report.event-queue}", containerFactory = "ReportListenerContainerFactory", admin = "ReportRabbitAdmin")
public class Consumer {

    private final ApplicationContext applicationContext;

//    private static MsgEnum[] onlyEvents = {MsgEnum.Recharge, MsgEnum.Betting, MsgEnum.UpAmount, MsgEnum.AddCoin,MsgEnum.Withdraw ,MsgEnum.BetEffectClear};

    @Autowired
    public Consumer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @RabbitHandler
    public void process(HashMap<String, Object> data, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
//            log.info("evlog接收到消息:{}", data);
//            Integer eventId = (Integer) data.get("event");
//            MsgEnum event = MsgEnum.valueOf(eventId);
//            if (event == null || !Arrays.asList(onlyEvents).contains(event)) {
//                channel.basicAck(tag, false);
//                return;
//            }
//            String eventKey = String.valueOf(eventId);
//            if (!data.containsKey(eventKey)) {
//                channel.basicAck(tag, false);
//                return;
//            }
//            Long timestamp = (Long) data.get("timestamp");
//            EventModel eventModel = new EventModel();
//            eventModel.setUid((Integer) data.get("uid"));
//            eventModel.setTimestamp(new Date(timestamp));
//            eventModel.setUuid(data.get("uuid").toString());
//            eventModel.setMsg((HashMap<String, Object>) data.get(eventKey));
//            String eventName = event.name();
//            EventHandle eventHandle = (EventHandle) applicationContext.getBean(eventName + "EventHandle");
//            eventHandle.process(eventModel);
            channel.basicAck(tag, false);
//            log.info("evlog消息处理完成:{}", data);
        } catch(Throwable e) {
            boolean reQueue = true;
            if (e instanceof NumberFormatException) {
                reQueue = false;
            }
            try {
                channel.basicReject(tag, reQueue);
            } catch (IOException ex) {
                log.error("report event reject error", ex);
            }
            log.error("report event consume error", e);
        }
    }
}