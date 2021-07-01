package com.hsm.事务;

import com.hsm.RocketConstants;
import com.hsm.ThreadUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

/**
 * @Classname 事务生产者
 * @Description TODO
 * @Date 2021/7/1 14:38
 * @Created by huangsm
 */
public class 事务生产者 {
    public static void main(String[] args) throws Exception{
        TransactionMQProducer producer = new TransactionMQProducer("transaction_producer_group");

        //线程池
        ExecutorService executorService = ThreadUtils.getInstance();
        //事务监听器
        TransactionListener transactionListener = new TransactionListenerImpl();

        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);

        producer.setNamesrvAddr(RocketConstants.NAME_SERVER_ADDRESS);

        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            try {
                Message msg =
                        new Message("TopicTest1234", tags[i % tags.length], "KEY" + i,
                                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction(msg, null);
                System.out.printf("%s%n", sendResult);

                ThreadUtils.sleep(10);
            } catch (MQClientException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 100000; i++) {
            ThreadUtils.sleep(1000);
        }
        producer.shutdown();
        producer.start();
    }
}
