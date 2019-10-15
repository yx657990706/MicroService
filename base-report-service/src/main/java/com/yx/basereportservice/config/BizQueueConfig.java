package com.yx.basereportservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jesse
 * 队列绑定
 */
@Slf4j
@Configuration
public class BizQueueConfig {
    @Value("${report.queue}")
    private String queue;

    @Value("${report.event-queue}")
    private String eventQueue;

    @Value("${report.exchange}")
    private String exchange;

    @Value("${report.routekey}")
    private String routeKey;

    @Value("${report.dead-queue}")
    private String deadQueue;

    @Value("${report.dead-exchange}")
    private String deadExchange;

    @Value("${report.dead-routekey}")
    private String deadRouteKey;

    /**
     * 主队列（添加了死信队列，消息TTL、被拒、失败会进入死信队列）
     * @return
     */
    @Bean
    public Queue reportQueue() {
        log.info("create queue:{}", this.queue);
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", this.deadExchange);
        args.put("x-dead-letter-routing-key", this.deadRouteKey);
        return new Queue(this.queue, true, false, false, args);
    }

    /**
     * 第二个持久化队列
     * @return
     */
    @Bean
    public Queue reportEventQueue() {
        log.info("create queue:{}", this.eventQueue);
        return new Queue(this.eventQueue, true, false, false);
    }

    /**
     *  主exchange
     * @return
     */
    @Bean
    public DirectExchange reportExchange() {
        log.info("create exchange:{}", this.exchange);
        return new DirectExchange(this.exchange, true, false);
    }

    /**
     * 绑定主队列和主交换机
     * @return
     */
    @Bean
    public Binding reportBinding() {
        log.info("create binding:{}", this.routeKey);
        return BindingBuilder.bind(reportQueue()).to(reportExchange()).with(this.routeKey);
    }

    /**
     *  绑定event队列和主交换机（绑定同一个交换机dirct模式，同1份数据发到2个队列中）
     * @return
     */
    @Bean
    public Binding reportEventBinding() {
        log.info("create binding:{}", this.routeKey);
        return BindingBuilder.bind(reportEventQueue()).to(reportExchange()).with(this.routeKey);
    }

    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue reportDeadQueue() {
        log.info("create dead queue:{}", this.deadQueue);
        return new Queue(this.deadQueue);
    }

    /**
     * 死信交换机
     * @return
     */
    @Bean
    public DirectExchange reportDeadExchange() {
        log.info("create dead exchange:{}", this.deadExchange);
        return new DirectExchange(this.deadExchange);
    }

    /**
     * 绑定死信队列和死信交换机
     * @return
     */
    @Bean
    public Binding reportDeadBinding() {
        log.info("create dead binding:{}", this.routeKey);
        return BindingBuilder.bind(reportDeadQueue()).to(reportDeadExchange()).with(this.deadRouteKey);
    }

}
