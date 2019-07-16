package com.yx.appclient.proxy;

import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * @author jesse
 * @version v1.0
 * @project MicroService
 * @Description
 * @encoding UTF-8
 * @date 2019/2/23
 * @time 8:59 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class MyTestController {

    public static void main(String[] args) {
        //要代理的真实对象
        BuyHouse buyHouse = new BuyHouseImpl();

        //要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
        DynamicProxy handler = new DynamicProxy(buyHouse);

        /**
         * 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
         * 第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
         * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
         * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上
         */
        BuyHouse proxyBuyHouse = (BuyHouse) Proxy.newProxyInstance(buyHouse.getClass().getClassLoader(), buyHouse.getClass().getInterfaces(), handler);
        System.out.println(proxyBuyHouse.getClass().getName());
        proxyBuyHouse.buyHosue();


//==================cglib动态代理===============
        Enhancer enhancer = new Enhancer();
        // 设置被代理的类（目标类）
        enhancer.setSuperclass(BuyHouseImpl.class);
        //使用回调
        enhancer.setCallback(new CglibProxy());
        // 创造 代理 （动态扩展了UserServiceImpl类）
        BuyHouseImpl my = (BuyHouseImpl) enhancer.create();
        //my.addUser("001", "centre");
        System.out.println(my.getClass().getName());
        my.buyHosue();

    }
}
