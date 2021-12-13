package com.hsm.java.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ObjectInvocationHandler implements InvocationHandler {
    private Object subject;

    public ObjectInvocationHandler(Object subject) {
        this.subject = subject;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("在调用之前，我要干点啥呢？");
        Object returnValue = method.invoke(subject, args);
        System.out.println("在调用之后，我要干点啥呢？");
        return returnValue;
    }
}
