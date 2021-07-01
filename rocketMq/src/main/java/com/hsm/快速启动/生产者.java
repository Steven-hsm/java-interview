package com.hsm.快速启动;

import com.alibaba.fastjson.JSON;
import com.hsm.RocketConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * @Classname 生成者
 * @Description TODO
 * @Date 2021/7/1 10:35
 * @Created by huangsm
 */
@Slf4j
public class 生产者 {
    public static void main(String[] args) throws Exception {
        try {
            //初始化一个生成者组
            DefaultMQProducer producer = new DefaultMQProducer("producer_group");
            //设置name server地址
            producer.setNamesrvAddr(RocketConstants.NAME_SERVER_ADDRESS);
            producer.start();

            for (int i = 0; i < 10; i++) {
                Message message = new Message("quick_start_topic", "tagA",
                        String.format("快速启动生成者的消息_%s", i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                //发送消息
                SendResult send = producer.send(message);
                log.info("发送消息结果：{}", JSON.toJSONString(send));
            }
            producer.shutdown();
        } catch (Exception e) {
            log.error("产生了异常", e);
        }
    }
}
