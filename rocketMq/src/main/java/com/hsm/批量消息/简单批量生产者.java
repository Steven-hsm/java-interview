package com.hsm.批量消息;

import com.alibaba.fastjson.JSON;
import com.hsm.RocketConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname 生产者
 * @Description 批量消费
 * @Date 2021/7/1 11:50
 * @Created by huangsm
 */
@Slf4j
public class 简单批量生产者 {
    public static void main(String[] args) throws Exception {
        try {
            //初始化一个生成者组
            DefaultMQProducer producer = new DefaultMQProducer("batch_producer_group");
            //设置name server地址
            producer.setNamesrvAddr(RocketConstants.NAME_SERVER_ADDRESS);
            producer.start();

            String topic = "BatchTest";
            List<Message> messages = new ArrayList<>();
            messages.add(new Message(topic, "Tag", "OrderID001", "Hello world 0".getBytes()));
            messages.add(new Message(topic, "Tag", "OrderID002", "Hello world 1".getBytes()));
            messages.add(new Message(topic, "Tag", "OrderID003", "Hello world 2".getBytes()));
            //发送批量消息
            SendResult send = producer.send(messages);

            log.info("发送消息结果：{}", JSON.toJSONString(send));
            producer.shutdown();
        } catch (Exception e) {
            log.error("产生了异常", e);
        }
    }
}
