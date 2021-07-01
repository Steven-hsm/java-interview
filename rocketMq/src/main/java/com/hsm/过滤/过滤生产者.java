package com.hsm.过滤;

import com.alibaba.fastjson.JSON;
import com.hsm.RocketConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @Classname 过滤生产者
 * @Description TODO
 * @Date 2021/7/1 14:13
 * @Created by huangsm
 */
@Slf4j
public class 过滤生产者 {
    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("filter_producer");
        producer.setNamesrvAddr(RocketConstants.NAME_SERVER_ADDRESS);

        producer.start();
        //消息发送到不同的tag上
        String[] tags = new String[]{"TagA", "TagB", "TagC"};

        for (int i = 0; i < 60; i++) {
            Message msg = new Message("FilterTopic",
                    tags[i % tags.length],
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            //给消息增加用户附加属性，可以用于sql过滤器
            msg.putUserProperty("a", String.valueOf(i));
            SendResult sendResult = producer.send(msg);
            log.info("发送结果：{}", JSON.toJSONString(sendResult));
        }
        producer.shutdown();
    }
}
