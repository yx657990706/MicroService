package com.yx.appcoreservicer.utils;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @Description
 * @encoding UTF-8
 * @date 2019/1/28
 * @time 1:33 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
@Slf4j
public class ReTryUtil {
//    ==========其主要接口及策略介绍：===================
//    Attempt：一次执行任务；
//    AttemptTimeLimiter：单次任务执行时间限制（如果单次任务执行超时，则终止执行当前任务）；
//    BlockStrategies：任务阻塞策略（通俗的讲就是当前任务执行完，下次任务还没开始这段时间做什么……），默认策略为：BlockStrategies.THREAD_SLEEP_STRATEGY 也就是调用 Thread.sleep(sleepTime);
//    RetryException：重试异常；
//    RetryListener：自定义重试监听器，可以用于异步记录错误日志；
//    StopStrategy：停止重试策略，提供三种：
//    StopAfterDelayStrategy ：设定一个最长允许的执行时间；比如设定最长执行10s，无论任务执行次数，只要重试的时候超出了最长时间，则任务终止，并返回重试异常RetryException；
//    NeverStopStrategy ：不停止，用于需要一直轮训直到返回期望结果的情况；
//    StopAfterAttemptStrategy ：设定最大重试次数，如果超出最大重试次数则停止重试，并返回重试异常；
//    WaitStrategy：等待时长策略（控制时间间隔），返回结果为下次执行时长：
//    FixedWaitStrategy：固定等待时长策略；
//    RandomWaitStrategy：随机等待时长策略（可以提供一个最小和最大时长，等待时长为其区间随机值）
//    IncrementingWaitStrategy：递增等待时长策略（提供一个初始值和步长，等待时间随重试次数增加而增加）
//    ExponentialWaitStrategy：指数等待时长策略；
//    FibonacciWaitStrategy ：Fibonacci 等待时长策略；
//    ExceptionWaitStrategy ：异常时长等待策略；
//    CompositeWaitStrategy ：复合时长等待策略；


    /**
     * 默认重试次数（3次）
     * @param callable
     * @return
     */
    public static boolean retryStopAfterAttemptBooleanDefault(Callable<Boolean> callable){
        return retryStopAfterAttemptBoolean(3,callable);
    }
    /**
     * 默认重试次数（3次）
     * @param callable
     * @return
     */
    public static <T>T retryStopAfterAttempt(Callable<T> callable){
        return retryStopAfterAttempt(3,callable);
    }
    /**
     * 指定重试次数
     * @param attemptNumber 重试次数
     * @param callable      需要重试的代码块封装的callable
     * @return boolean
     */
    public static boolean retryStopAfterAttemptBoolean(int attemptNumber,Callable<Boolean> callable){
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Predicates.<Boolean>isNull())//重试条件：调用结果为空
                .retryIfException()//重试条件：发生异常
                .withStopStrategy(StopStrategies.stopAfterAttempt(attemptNumber))//重试策略
//                .withWaitStrategy(WaitStrategies.fixedWait(100, TimeUnit.MILLISECONDS))//等待时长策略
                .withWaitStrategy(WaitStrategies.fibonacciWait(100,10,TimeUnit.SECONDS))
                .build();
        try {
            return  retryer.call(callable);
        } catch (ExecutionException e) {
            log.error(e.getMessage(),e);
        } catch (RetryException e) {
            log.error(e.getMessage(),e);
        }
        log.info("===>>重试失败～");
        return false;
    }

    /**
     * 指定重试次数
     * @param attemptNumber
     * @param callable
     * @return string
     */
    public static <T>T retryStopAfterAttempt(int attemptNumber,Callable<T> callable){
        Retryer<T> retryer = RetryerBuilder.<T>newBuilder()
                .retryIfResult(Predicates.<T>isNull())//重试条件：调用结果为空
                .retryIfException()//重试条件：发生异常
                .withStopStrategy(StopStrategies.stopAfterAttempt(attemptNumber))//重试策略
//                .withWaitStrategy(WaitStrategies.fixedWait(100, TimeUnit.MILLISECONDS))//等待时长策略
                .withWaitStrategy(WaitStrategies.fibonacciWait(100,10,TimeUnit.SECONDS))//等待时长策略
                .build();
        try {
            return  retryer.call(callable);
        } catch (ExecutionException e) {
            log.error(e.getMessage(),e);
        } catch (RetryException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 按时间间隔重试<br>
     * minutes分钟内，以斐波那契退避间隔等待，直到最多minutes分钟。minutes分钟后，每隔minutes分钟重试一次。
     * @param minutes  重试间隔时间（分钟）
     * @param callable
     * @return
     */
    public static <T> T retryNeverStop(int minutes,Callable<T> callable){
        Retryer<T> retryer = RetryerBuilder.<T>newBuilder()
                .retryIfResult(Predicates.<T>isNull())
                .retryIfException()
                .withWaitStrategy(WaitStrategies.fibonacciWait(100, minutes, TimeUnit.MINUTES))
                .withStopStrategy(StopStrategies.neverStop())
                .build();
        try {
            return  retryer.call(callable);
        } catch (ExecutionException e) {
            log.error(e.getMessage(),e);
        } catch (RetryException e) {
            log.error(e.getMessage(),e);
        }
        log.info("===>>重试失败～");
        return null;
    }

//    /**
//     * Callable示例
//     * 根据异常判断是否重试
//     * @return
//     */
//    private Callable<String> callableWithException() {
//        return new Callable<String>() {
//            int counter = 0;
//            public String call() throws Exception {
//                counter++;
//                log.info("do sth : {}", counter);
//                if (counter < 5) {
//                    throw new RuntimeException("sorry");
//                }
//                return "good";
//            }
//        };
//    }

}
