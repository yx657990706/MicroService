package com.yx.basereportservice.amqp;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.yx.basecoreservice.utils.SpringUtils;
import com.yx.basereportservice.constant.QueueMessgeConst;
import com.yx.basereportservice.model.QueueMessge;
import com.yx.basereportservice.service.DealBizQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2018/12/17
 * @time 5:21 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Slf4j
@Component
public class QueueConsumer {


    @RabbitListener(queues = "${report.queue}")
    @RabbitHandler
    public void dealBiz(QueueMessge queueMessge, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            log.info("===>>消费:::{}", JSON.toJSONString(queueMessge));
            final HashMap<String, Object> content = queueMessge.getContent();
            final Object o = content.get(QueueMessgeConst.BEAN_NAME_KEY);
//            final Object bean = SpringUtils.getBean(o.toString());
////            DealBizQueueService dealBizQueueService = (DealBizQueueService) bean;
////            if (dealBizQueueService != null) {
////                dealBizQueueService.process(queueMessge);
////            }
            channel.basicAck(tag, false);
            log.info("QueueConsumer消息处理完成:{}", queueMessge);
        } catch (Throwable e) {
            boolean reQueue = true;
            try {
                channel.basicReject(tag, reQueue);//reject后进入死信队列
            } catch (IOException ex) {
                log.error("report event reject error", ex);
            }
            log.error("report event consume error", e);
        }
    }
}
