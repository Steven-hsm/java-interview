## 1.org.apache.rocketmq.client.exception.MQClientException: No route info of this topic, MyTopic

报错信信息如下：

```java
Exception in thread "main" org.apache.rocketmq.client.exception.MQClientException: No route info of this topic, MyTopic
See http://rocketmq.apache.org/docs/faq/ for further details.
	at org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl.sendDefaultImpl(DefaultMQProducerImpl.java:610)
	at org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl.sendOneway(DefaultMQProducerImpl.java:881)
	at org.apache.rocketmq.client.producer.DefaultMQProducer.sendOneway(DefaultMQProducer.java:285)
```

[解决方案参考](https://blog.csdn.net/chenaima1314/article/details/79403113)

* 主要原因可能

  * Broker禁止自动创建Topic，且用户没有通过手工方式创建Topic

    * 解决方案：，启动顺序要先启动nameserver，再启动broker，启动broker时加上autoCreateTopicEnable=true 

  * Broker没有正确连接到Name Server

    * 查看broker日志：出现以下日志连接成功

      ```shell
      2018-02-28 16:21:35 INFO BrokerControllerScheduledThread1 - register broker to name server 192.168.192.129:9876 OK
      2018-02-28 16:22:05 INFO BrokerControllerScheduledThread1 - register broker to name server 192.168.192.129:9876 OK
      ```

  * Producer没有正确连接到Name Server 

    * 在bin目录下执行命令sh mqadmin clusterList -n localhost:9876 如果看到以下日志表示成功

      ```shell
      #Cluster Name     #Broker Name            #BID  #Addr                  #Version                #InTPS(LOAD)       #OutTPS(LOAD) #PCWait(ms) #Hour #SPACE
      DefaultCluster    DEFAULT_BROKER          0     192.168.192.129:10911  V4_2_0_SNAPSHOT          0.00(0,0ms)         0.00(0,0ms)          0 422168.55 -1.0000
      ```

  * 防火墙没有关闭

    * 解决方法：执行 ```shell systemctl stop firewalld.service```

## 2.rocketmq在console控制台中找不到生产组
生产者在调用 producer.shutdown();的时候，自动将这个组删掉了， 所以查找的时候找不到了