package com.hsm.java.netty.dubborpc.provider;

import com.hsm.java.netty.dubborpc.publicinterface.HelloService;

/**
 * @Classname HelloServiceImpl
 * @Description TODO
 * @Date 2021/8/10 20:08
 * @Created by huangsm
 */
public class HelloServiceImpl implements HelloService {
    private static int count = 0;

    //当有消费方调用该方法时， 就返回一个结果
    @Override
    public String hello(String mes) {
        System.out.println("收到客户端消息=" + mes);
        return "hello";
    }

    @Override
    public String test(String mes) {
        return "返回测试消息";
    }
}
