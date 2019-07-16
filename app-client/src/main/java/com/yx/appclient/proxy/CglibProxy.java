package com.yx.appclient.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author jesse
 * @version v1.0
 * @project MicroService
 * @Description
 * @encoding UTF-8
 * @date 2019/2/23
 * @time 11:34 PM
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class CglibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("调用的方法是：" + method.getName());
        Object ret = null;
        try {
            ret = methodProxy.invokeSuper(obj, objects);
            System.out.print("成功调用方法：" + method.getName() + ";参数为：");
            for (int i = 0; i < objects.length; i++) {
                System.out.print(objects[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("调用方法：" + method.getName() + "失败;参数为：");
            for (int i = 0; i < objects.length; i++) {
                System.out.print(objects[i]);
            }
        }
        return ret;
    }
}
