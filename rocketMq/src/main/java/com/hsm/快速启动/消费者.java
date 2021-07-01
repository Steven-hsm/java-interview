package com.hsm.快速启动;

import com.hsm.RocketConstants;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Classname 消费者
 * @Description TODO
 * @Date 2021/7/1 11:40
 * @Created by huangsm
 */
public class 消费者 {
    public static void main(String[] args) throws InterruptedException, MQClientException {
        //初始化一个消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("producer_consumer");
        //设置name server地址
        consumer.setNamesrvAddr(RocketConstants.NAME_SERVER_ADDRESS);
        //订阅主题
        consumer.subscribe("quick_start_topic", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.println("消费者启动完毕");
    }
}
