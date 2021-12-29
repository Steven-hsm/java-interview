package com.hsm.测试根据Tag区分消费者;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Arrays;
import java.util.List;

public class 生产者 {
    public static void main(String[] args) throws Exception{
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("myProducer");
        // 设置NameServer的地址
        producer.setNamesrvAddr("192.168.148.129:9876");
        // 启动Producer实例
        producer.start();
        List<String> tagList = Arrays.asList("TagA", "TagB", "TagC");
        for (int i = 0; i < 3; i++) {
            String tag = tagList.get(i);
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicTest", tag ,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送单向消息，没有任何返回结果
            producer.sendOneway(msg);
        }
        // 如果不再发送消息，关闭Producer实例。这里会把消费者组删除掉
        producer.shutdown();
    }
}
