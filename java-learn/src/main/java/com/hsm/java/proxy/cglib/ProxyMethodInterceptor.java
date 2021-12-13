package com.hsm.java.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("在调用之前，我要干点啥呢？");
        Object returnValue = methodProxy.invokeSuper(target, args);
        System.out.println("在调用之后，我要干点啥呢？");
        return returnValue;
    }
}
