package com.hsm.批量消息;

import com.alibaba.fastjson.JSON;
import com.hsm.RocketConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname 批量消息迭代器
 * @Description TODO
 * @Date 2021/7/1 12:03
 * @Created by huangsm
 */
@Slf4j
public class 批量消息迭代器 {
    public static void main(String[] args) throws Exception {
        try {
            //初始化一个生成者组
            DefaultMQProducer producer = new DefaultMQProducer("split_producer_group");
            //设置name server地址
            producer.setNamesrvAddr(RocketConstants.NAME_SERVER_ADDRESS);
            producer.start();

            String topic = "splitBatchTest";
            List<Message> messages = new ArrayList<>();
            messages.add(new Message(topic, "Tag", "OrderID001", "Hello world 0".getBytes()));
            messages.add(new Message(topic, "Tag", "OrderID002", "Hello world 1".getBytes()));
            messages.add(new Message(topic, "Tag", "OrderID003", "Hello world 2".getBytes()));

            //使用自定迭代一，每次发送一定数量数据
            ListSplitter splitter = new ListSplitter(messages);
            while (splitter.hasNext()) {
                List<Message> listItem = splitter.next();
                //发送批量消息
                SendResult send = producer.send(listItem);
                log.info("发送消息结果：{}", JSON.toJSONString(send));

            }
            producer.shutdown();
        } catch (Exception e) {
            log.error("产生了异常", e);
        }
    }
}
