package com.hsm.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @Classname 生产者单向发送消息
 * @Description TODO
 * @Date 2021/6/8 14:12
 * @Created by huangsm
 */
public class 生产者单向发送消息 {
    public static void main(String[] args) throws Exception{
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("myConsumer");
        // 设置NameServer的地址
        producer.setNamesrvAddr("192.168.0.164:9876");
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 1; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送单向消息，没有任何返回结果
            producer.sendOneway(msg);

        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
