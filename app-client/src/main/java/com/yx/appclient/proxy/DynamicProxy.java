package com.yx.appclient.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author jesse
 * @version v1.0
 * @project MicroService
 * @Description
 * @encoding UTF-8
 * @date 2019/2/23
 * @time 9:34 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class DynamicProxy implements InvocationHandler {
    /**
     * 需要被代理的对象
     */
    private Object object;

    /**
     *  构造方法，给我们要代理的真实对象赋初值
     * @param obj
     */
    public DynamicProxy(Object obj){
        this.object = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //　　在代理真实对象前我们可以添加一些自己的操作
        System.out.println("before");
        System.out.println("Method:" + method);
        //    当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        Object result = method.invoke(object, args);
        //　　在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("after");

        return result;
    }
}
