package com.yx.appclient.proxy;

import java.util.Random;

/**
 * @author jesse
 * @version v1.0
 * @project MicroService
 * @Description
 * @encoding UTF-8
 * @date 2019/2/22
 * @time 10:56 AM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class StaticProxy implements BuyHouse {
    private BuyHouse buyHouse;

    public StaticProxy(final BuyHouse buyHouse) {
        this.buyHouse = buyHouse;
    }

    @Override
    public void buyHosue() {
        Random rand = new Random();
        //代理模式可以控制代理对象
        if (rand.nextBoolean()) {
            System.out.println("买房前准备");
            buyHouse.buyHosue();
            System.out.println("买房后装修");
        }else{
            System.out.println("太贵了，买不起");
        }
    }
}
