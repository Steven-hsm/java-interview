## Springboot 快速集成RocketMq

### 1. 增加pom文件

   ```xml
<dependency>
    <groupId>org.hongxi</groupId>
    <artifactId>rocketmq-spring-boot-starter</artifactId>
</dependency>
   ```

### 2.  配置

   ```yaml
rocketmq:
  name-server: 192.168.0.84:9876
  producer:
    group: test-group
  sendMsgTimeoutMillis: 3000
  reconsumeTimes: 3
   ```

### 3. 登录rocketmq管理后台添加topic（operation-topic）

![](https://img2020.cnblogs.com/blog/871616/202108/871616-20210809162243473-1449357702.png)


### 4. 生产者使用

```java
@Autowired
RocketMQTemplate rocketMQTemplate;

@Test
public void sendHelloWorld() {
    SendResult result = rocketMQTemplate.syncSend("operation-topic", "hello world");
    log.info("发送结果：{}", JSON.toJSONString(result));
}
```

### 5. 消费者使用

```java
@Component
@RocketMQMessageListener(topic = "operation-topic", //topic主题
        consumerGroup = "consumer-group",          //消费组
        messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.ORDERLY)
@Slf4j
public class MqTestListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("接受到消息：{}", message);
    }
}
```

### 6. 注意事项

**如何使用tag**

```java
/**
  * Same to {@link #syncSend(String, Message)}.
  *
  * @param destination formats: `topicName:tags`
  * @param payload     the Object to use as payload
  * @return {@link SendResult}
  */
public SendResult syncSend(String destination, Object payload) {
    return syncSend(destination, payload, producer.getSendMsgTimeout());
}
```

这里可以看到 destination  = topicName:tags

**消费模式**

* 广播模式 MessageModel.BROADCASTING 所有消费者都会收到消息
* 集群模式 MessageModel.CLUSTERING 只有一个消费者会消费，类似于负载均衡

消费者如果存在一个为广播模式，消费模式都会广播模式