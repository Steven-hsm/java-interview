阿里计费示例：

```tex
计费示例
假设您创建的实例在杭州地域，该实例上创建的Topic数量及消息收发数据如下：

创建的Topic数量：20个
每天生产的消息总数：100万条（50万普通消息+50万高级特性消息）
消息的生产和投递比：1（生产的消息总数和投递的消息总数各为100万条。）
每条消息平均大小：40 KB
则该实例一天的消息收发费用如下：
API调用费用：
API调用次数为（50万+50万×5）×2×（40/4）=6000万次

单价按API调用次数阶梯计费，6000万条在第一阶梯内，以单价2元/百万条计算，消息收发API调用费用为（6000/100）×2=120元。

Topic资源占用费用：
Topic个数为20个，单价按照API调用次数阶梯计费。单个Topic每日API调用次数为6000万/20=300万次，在第二阶梯，以1.5元/个/日单价计算，Topic资源占用费用为20×1.5=30元。
```

公司内部主要付费在topic数量上，Api调用次数还没有达到那么高的调用次数，但是由于业务繁多，topic数量反而在持续升高，造成每个月在topic上的费用不低

**那么能不能尽量减少topic的数量**

### 不同的业务场景使用相同的topic

| 消费者   | 主题      | 消费者组   | 消费tag |
| -------- | --------- | ---------- | ------- |
| 消费TagA | TopicTest | myConsumer | TagA    |
| 消费TagB | TopicTest | myConsumer | TagB    |
| 消费TagC | TopicTest | myConsumer | TagB    |

这里建立了三个消费者，订阅相同的主题，相同的消费者组，监听不同的消费Tag

**测试后发现问题**

> 最后一个消费者会使之前的消费者失效，比如启动顺序为消费者A,消费者B,消费者C依次启动，造成的结果就是消费者A无法监听TagA上的消息，消费者B无法监听TagB上的消息，消费者C可以正常监听TagC的消息。 暂时没有关注源码，底层原理不是很清楚

**改进措施**

| 消费者   | 主题      | 消费者组     | 消费tag |
| -------- | --------- | ------------ | ------- |
| 消费TagA | TopicTest | TagAConsumer | TagA    |
| 消费TagB | TopicTest | TagBConsumer | TagB    |
| 消费TagC | TopicTest | TagCConsumer | TagB    |

这里和上面的区别就是消费者组不再是同一个消费者组了，然后测试完全正常

### 测试代码

1. 消费TagA

   ```java
   public class 消费TagA {
       public static void main(String[] args) throws Exception{
           DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TagAConsumer");
           // 订阅Topics
           consumer.setConsumeThreadMax(1);
           consumer.setConsumeThreadMin(1);
           consumer.setNamesrvAddr("192.168.148.129:9876");
           consumer.subscribe("TopicTest", "TagA");
           // 注册消息监听者
           consumer.registerMessageListener(new MessageListenerConcurrently() {
               @Override
               public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext
                       context) {
                   for (MessageExt message : messages) {
                       // Print approximate delay time period
                       System.out.println("Receive message[msgId=" + message.getMsgId() + "] " + (System.currentTimeMillis() - message.getStoreTimestamp()) + "ms later");
                   }
                   try {
                       Thread.sleep(1000 * 10);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
               }
           });
           // 启动消费者
           consumer.start();
       }
   ```

2. 消费TagB

   ```java
    //只需要修改tag
    consumer.subscribe("TopicTest", "TagB");
   ```

3. 消费TagC

   ```java
    //只需要修改tag
    consumer.subscribe("TopicTest", "TagC");
   ```

4. 生产者

   ```java
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
   ```

   

