package com.hsm.过滤;

import com.hsm.RocketConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.IOException;
import java.util.List;

/**
 * @Classname 标签过滤消费者
 * @Description TODO
 * @Date 2021/7/1 14:18
 * @Created by huangsm
 */
@Slf4j
public class 标签过滤消费者 {
    public static void main(String[] args) throws InterruptedException, MQClientException, IOException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");
        consumer.setNamesrvAddr(RocketConstants.NAME_SERVER_ADDRESS);
        //只监听标签为TagA和TagC
        consumer.subscribe("FilterTopic", "TagA || TagC");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                log.info("接收到的消息：{}" , msgs.toString());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
