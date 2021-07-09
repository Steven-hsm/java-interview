package com.hsm.rocketMq.basic;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.utils.ThreadUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @Classname SpringProduct
 * @Description spring生产者
 * @Date 2021/7/3 9:43
 * @Created by huangsm
 */
@Component
@Slf4j
public class SpringProduct {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage(String topic, String msg) {
        this.rocketMQTemplate.convertAndSend(topic, msg);
    }

    public void sendMessageIntransaction(String topic, String msg) throws InterruptedException {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder.withPayload(msg)
                    .setHeader(RocketMQHeaders.TRANSACTION_ID, "TransID_" + i)
                    .setHeader(RocketMQHeaders.TAGS, tags[i % tags.length])
                    .setHeader("mypro", "mypro_" + i)
                    .build();

            String destination = topic + ":" + tags[i % tags.length];
            TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction("transaction",destination, message, destination);
            log.info("发送消息结果：{}", JSON.toJSONString(result));
            Thread.sleep(10);        }
    }
}
