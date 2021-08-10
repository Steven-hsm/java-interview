package com.hsm.java.netty.dubborpc.consumer;

import com.hsm.java.netty.dubborpc.publicinterface.HelloService;

/**
 * @Classname ClientBootstrap
 * @Description TODO
 * @Date 2021/8/10 20:12
 * @Created by huangsm
 */
public class ClientBootstrap {
    //服务接口
    public static final String SERVICE_NAME = "HelloService";
    //服务方法
    public static final String METHOD_NAME_TEST = "test";
    public static final String METHOD_NAME_HELLO = "hello";

    public static void main(String[] args) throws Exception {

        //创建一个消费者
        NettyClient customer = new NettyClient();

        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, SERVICE_NAME);

        for (; ; ) {
            Thread.sleep(2 * 1000);
            //通过代理对象调用服务提供者的方法(服务)
            String res = service.test("你好 dubbo~");
            System.out.println("test调用的结果 res= " + res);
            String res2 = service.hello("你好 dubbo~");
            System.out.println("hello调用的结果 res= " + res2);
        }
    }
}
