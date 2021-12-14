pom.xml

```xml
<dependency>
    <groupId>org.apache.rocketmq</groupId>
    <artifactId>rocketmq-client</artifactId>
    <version>4.7.1</version>
</dependency>
```

### 1. RocketMQ的编程模型

* 消息发送者

  1. 创建消息生产者producer，并指定生产者组名
  2. 指定Nameserver地址
  3. 启动producer
  4. 创建消息对象，指定主题Topic、Tag和消息体
  5. 发送消息
  6. 关闭生产者producer
* 消息消费者

  1. .创建消费者Consumer，指定消费者组名
  2. 指定Nameserver地址
  3. 订阅主题Topic和Tag
  4. 设置回调函数，处理消息
  5. 启动消费者consumer


### 2. 消息发送demo

#### 2.1 单向消息

没有返回值，也没有回调。就是只管把消息发出去就行了。

```java
public class 生产者单向发送消息 {
    public static void main(String[] args) throws Exception{
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("myConsumer");
        // 设置NameServer的地址
        producer.setNamesrvAddr("192.168.148.133:9876");
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
        // 如果不再发送消息，关闭Producer实例。这里会把消费者组删除掉
        producer.shutdown();
    }
}
```

#### 2.2 同步消息

同步消息存在返回值，可以获取发送结果

```java
public class 生产者发送同步消息 {
    public static void main(String[] args) throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("myProducer");
        // 设置NameServer的地址
        producer.setNamesrvAddr("192.168.148.133:9876");
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 100; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicTest2" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
```

#### 2.3 异步消息

异步消息存在消息回调，在回调中处理响应结果

```java
public class 生产者发送异步消息 {
    public static void main(String[] args) throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("myProducer");
        // 设置NameServer的地址
        producer.setNamesrvAddr("192.168.106.133:9876");
        // 启动Producer实例
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 100;
        // 根据消息数量实例化倒计时计算器
        final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);
        for (int i = 0; i < messageCount; i++) {
            final int index = i;
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicTest",
                    "TagA",
                    "OrderID188",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            // SendCallback接收异步返回结果的回调
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }
                @Override
                public void onException(Throwable e) {
                    countDownLatch.countDown();
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        // 等待5s
        countDownLatch.await(5, TimeUnit.SECONDS);
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
```

#### 2.4 顺序消息

生产者

```java
public class 顺序生产消息 {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("myConsumer");

        producer.setNamesrvAddr("192.168.148.133:9876");
        producer.start();

        String[] tags = new String[]{"TagA", "TagC", "TagD"};
        // 订单列表
        List<OrderStep> orderList = new 顺序生产消息().buildOrders();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        for (int i = 0; i < 10; i++) {
            // 加个时间前缀
            String body = dateStr + " Hello RocketMQ " + orderList.get(i);
            Message msg = new Message("TopicOrder", tags[i % tags.length], "KEY" + i, body.getBytes());

            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Long id = (Long) arg;  //根据订单id选择发送queue
                    long index = id % mqs.size();
                    return mqs.get((int) index);
                }
            }, orderList.get(i).getOrderId());//订单id

            System.out.println(String.format("SendResult status:%s, queueId:%d, body:%s",
                    sendResult.getSendStatus(),
                    sendResult.getMessageQueue().getQueueId(),
                    body));
        }

        producer.shutdown();
    }

    /**
     * 订单的步骤
     */
    private static class OrderStep {
        private long orderId;
        private String desc;

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "OrderStep{" +
                    "orderId=" + orderId +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }

    /**
     * 生成模拟订单数据
     */
    private List<OrderStep> buildOrders() {
        List<OrderStep> orderList = new ArrayList<OrderStep>();

        OrderStep orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103117235L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(15103111039L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        return orderList;
    }
}
```

消费者

```java
public class 顺序消费消息 {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("myProducer");
        consumer.setNamesrvAddr("192.168.148.133:9876");
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicOrder", "TagA || TagC || TagD");
        consumer.registerMessageListener(new MessageListenerOrderly() {

            Random random = new Random();

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                for (MessageExt msg : msgs) {
                    // 可以看到每个queue有唯一的consume线程来消费, 订单对每个queue(分区)有序
                    System.out.println("consumeThread=" + Thread.currentThread().getName() + "queueId=" + msg.getQueueId() + ", content:" + new String(msg.getBody()));
                }
                try {
                    //模拟业务逻辑处理中...
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer Started.");
    }
}
```

#### 2.5 广播消息

广播消息并没有特定的消息消费者样例，这是因为这涉及到消费者的集群消费模式。在集群状态(MessageModel.CLUSTERING)下，每一条消息只会被同一个消费者组中的一个实例消费到(这跟kafka和rabbitMQ的集群模式是一样的)。而广播模式则是把消息发给了所有订阅了对应主题的消费者，而不管消费者是不是同一个消费者组。

```java
public class push消费者 {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("broad_consumer");
        //设置消息模式为广播模式，广播模式是所有的消费者都可以接收到消息
        consumer.setMessageModel(MessageModel.BROADCASTING);
        //订阅主题
        consumer.subscribe("TopicTest", "TagA || TagC || TagD");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.printf("Broadcast Consumer Started.%n");
    }
}
```

#### 2.6 延迟消息

延迟消息实现的效果就是在调用producer.send方法后，消息并不会立即发送出去，而是会等一段时间再发送出去。

```java
public class 发送延时消息 {
    public static void main(String[] args) throws Exception {
        // 实例化一个生产者来产生延时消息
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        // 启动生产者
        producer.setNamesrvAddr("192.168.106.133:9876");
        producer.start();
        int totalMessagesToSend = 100;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
            // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
            message.setDelayTimeLevel(3);
            // 发送消息
            producer.send(message);
        }
        // 关闭生产者
        producer.shutdown();
    }
}
```

#### 2.7 批量消息

批量消息是指将多条消息合并成一个批量消息，一次发送出去。这样的好处是可以减少网络IO，提升吞吐量

1. 简单批量生产者

   ```java
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
   ```

2. 批量消息迭代器

   ```java
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
   public class ListSplitter implements Iterator<List<Message>> {
       private int sizeLimit = 1000 * 1000;
       private final List<Message> messages;
       private int currIndex;
   
       public ListSplitter(List<Message> messages) {
           this.messages = messages;
       }
   
       @Override
       public boolean hasNext() {
           return currIndex < messages.size();
       }
   
       @Override
       public List<Message> next() {
           int nextIndex = currIndex;
           int totalSize = 0;
           for (; nextIndex < messages.size(); nextIndex++) {
               Message message = messages.get(nextIndex);
               int tmpSize = message.getTopic().length() + message.getBody().length;
               Map<String, String> properties = message.getProperties();
               for (Map.Entry<String, String> entry : properties.entrySet()) {
                   tmpSize += entry.getKey().length() + entry.getValue().length();
               }
               tmpSize = tmpSize + 20; //for log overhead
               if (tmpSize > sizeLimit) {
                   //it is unexpected that single message exceeds the sizeLimit
                   //here just let it go, otherwise it will block the splitting process
                   if (nextIndex - currIndex == 0) {
                       //if the next sublist has no element, add this one and then break, otherwise just break
                       nextIndex++;
                   }
                   break;
               }
               if (tmpSize + totalSize > sizeLimit) {
                   break;
               } else {
                   totalSize += tmpSize;
               }
   
           }
           List<Message> subList = messages.subList(currIndex, nextIndex);
           currIndex = nextIndex;
           return subList;
       }
   
       @Override
       public void remove() {
           throw new UnsupportedOperationException("Not allowed to remove");
       }
   }
   ```

3. 消费者

   ```java
   public class 消费者 {
       public static void main(String[] args) throws InterruptedException, MQClientException {
           //初始化一个消费者组
           DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("batch_consumer_group");
           consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
           //设置name server地址
           consumer.setNamesrvAddr(RocketConstants.NAME_SERVER_ADDRESS);
           //订阅主题
           consumer.subscribe("BatchTest", "*");
           consumer.registerMessageListener(new MessageListenerConcurrently() {
               @Override
               public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                               ConsumeConcurrentlyContext context) {
                   System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                   return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
               }
           });
           consumer.start();
           System.out.println("消费者启动完毕");
       }
   }
   ```

#### 2.8 过滤消息

在大多数情况下，可以使用Message的Tag属性来简单快速的过滤信息。这种方式有一个很大的限制，就是一个消息只能有一个TAG，可以使用SQL表达式来对消息进行过滤。

SQL92语法：

* 数值比较，比如：**>**，**>=**，**<**，**<=**，**BETWEEN**，**=**
* 字符比较，比如：**=**，<>，IN；
* IS NULL** 或者 **IS NOT NULL**；
* 逻辑符号 **AND****，**OR**，***NOT**；
* 常量支持类型为：
  * 数值，比如：**123**，**3.1415**；
  * 字符，比如：**'abc'**，必须用单引号包裹起来；
  * **NULL**，特殊的常量
  * 布尔值，**TRUE** 或 FALSE

1. 生成者

   ```java
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
   ```

2. sql过滤器

   ```java
   public class sql过滤器 {
       public static void main(String[] args) throws Exception {
   
           DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("sql_filter_topic");
           consumer.setNamesrvAddr(RocketConstants.NAME_SERVER_ADDRESS);
   
           //
           consumer.subscribe("FilterTopic",
                   MessageSelector.bySql("(TAGS is not null and TAGS in('TagB'))" +
                           "and (a is not null and a > 10)"));
   
           consumer.registerMessageListener(new MessageListenerConcurrently() {
   
               @Override
               public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                               ConsumeConcurrentlyContext context) {
                   System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                   return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
               }
           });
   
           consumer.start();
           System.out.printf("Consumer Started.%n");
       }
   }
   ```

3. 标签过滤器

   ```java
   @Slf4j
   public class 标签过滤消费者 {
       public static void main(String[] args) throws InterruptedException, MQClientException, IOException {
   
           DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");
           consumer.setNamesrvAddr(RocketConstants.NAME_SERVER_ADDRESS);
           //只监听标签为TagA和TagC
           consumer.subscribe("FilterTopic", "TagA || TagC");
   
           consumer.registerMessageListener(new MessageListenerConcurrently() {
               @Override
               public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                               ConsumeConcurrentlyContext context) {
                   log.info("接收到的消息：{}" , msgs.toString());
                   return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
               }
           });
           consumer.start();
           System.out.printf("Consumer Started.%n");
       }
   }
   ```

#### 2.9 事务消息

这个事务消息是RocketMQ提供的一个非常有特色的功能。事务消息是在分布式系统中保证最终一致性的两阶段提交的消息实现。他可以保证本地事务执行与消息发送两个操作的原子性，也就是这两个操作一起成功或者一起失败。

事务消息只保证消息发送者的本地事务与发消息这两个操作的原子性，因此，事务消息的示例只涉及到消息发送者，对于消息消费者来说，并没有什么特别的

```java
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
public class TransactionListenerImpl implements TransactionListener {
    private AtomicInteger transactionIndex = new AtomicInteger(0);
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        int value = transactionIndex.getAndIncrement();
        int status = value % 3;
        localTrans.put(message.getTransactionId(), status);
        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        Integer status = localTrans.get(messageExt.getTransactionId());
        if (null != status) {
            switch (status) {
                case 0:
                    return LocalTransactionState.UNKNOW;
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                default:
                    return LocalTransactionState.COMMIT_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
```

事务限制：

* 事务消息不支持延迟消息和批量消息
* 为了避免单个消息被检查太多次而导致半队列消息累积，我们默认将单个消息的检查次数限制为15 次，但是用户可以通过 Broker 配置文件的 transactionCheckMax 参数来修改此限制
* 事务消息将在 Broker 配置文件中的参数 transactionMsgTimeout 这样的特定时间长度之后被检查。当发送事务消息时，用户还可以通过设置用户属性 CHECK_IMMUNITY_TIME_IN_SECONDS 来改变这个限制，该参数优先于 transactionMsgTimeout 参数。
* 事务性消息可能不止一次被检查或消费。
* 提交给用户的目标主题消息可能会失败，目前这依日志的记录而定。
* 事务消息的生产者 ID 不能与其他类型消息的生产者 ID 共享

#### 2.10 ACL控制

权限控制（ACL）主要为RocketMQ提供Topic资源级别的用户访问控制。用户在使用RocketMQ权限控制时，可以在Client客户端通过 RPCHook注入AccessKey和SecretKey签名；同时，将对应的权限控制属性（包括Topic访问权限、IP白名单和AccessKey和SecretKey签名等）设置在$ROCKETMQ_HOME/conf/plain_acl.yml的配置文件中。Broker端对AccessKey所拥有的权限进行校验，校验不过，抛出异常；

broker.conf增加```aclEnable=true```

plan_acl.ym修改

```yaml
#全局白名单，不受ACL控制
#通常需要将主从架构中的所有节点加进来
globalWhiteRemoteAddresses:
- 10.10.103.*
- 192.168.0.*

accounts:
#第一个账户
- accessKey: RocketMQ
  secretKey: 12345678
  whiteRemoteAddress:
  admin: false
  defaultTopicPerm: DENY#默认Topic访问策略是拒绝
  defaultGroupPerm: SUB#默认Group访问策略是只允许订阅
  topicPerms:
  - topicA=DENY#topicA拒绝
  - topicB=PUB|SUB#topicB允许发布和订阅消息
  - topicC=SUB#topicC只允许订阅
  groupPerms:
  # the group should convert to retry topic
  - groupA=DENY
  - groupB=PUB|SUB
  - groupC=SUB
#第二个账户，只要是来自192.168.1.*的IP，就可以访问所有资源
- accessKey: rocketmq2
  secretKey: 12345678
  whiteRemoteAddress: 192.168.1.*
  # if it is admin, it could access all resources
  admin: true
```

修改配置文件就可以了，不用重启Broker服务

```xml
<dependency>
    <groupId>org.apache.rocketmq</groupId>
    <artifactId>rocketmq-client</artifactId>
    <version>4.7.1</version>
</dependency>

```



