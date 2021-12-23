### 1.**Zookeeper Java 客户端** 

```xml
<dependency>
    <groupId>org.apache.zookeeper</groupId>
    <artifactId>zookeeper</artifactId>
    <version>3.5.8</version>
</dependency>
```

```java
@Slf4j
public class ZookeeperClientTest {
    private static final String ZK_ADDRESS = "192.168.148.129:2181";
    private static final int SESSION_TIMEOUT = 5000;
    private static ZooKeeper zooKeeper;
    private static final String ZK_NODE = "/hsm‐node";


    public void init() throws IOException, InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        zooKeeper = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected && event.getType() == Watcher.Event.EventType.None) {
                countDownLatch.countDown();
                log.info("连接成功！");
            }
        });
        log.info("连接中....");
        countDownLatch.await();
    }

    public void createTest() throws KeeperException, InterruptedException {
        String path = zooKeeper.create(ZK_NODE, "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        log.info("created path: {}",path);
    }

    public void createAsycTest() throws InterruptedException {
        zooKeeper.create(ZK_NODE, "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,  CreateMode.PERSISTENT,
                (rc, path, ctx, name) -> {
            log.info("rc {},path {},ctx {},name {}",rc,path,ctx,name);
        },"context");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    public void setTest() throws KeeperException, InterruptedException {
        Stat stat = new Stat();
        byte[] data = zooKeeper.getData(ZK_NODE, false, stat);
        log.info("修改前: {}",new String(data));
        zooKeeper.setData(ZK_NODE, "changed!".getBytes(), stat.getVersion());
        byte[] dataAfter = zooKeeper.getData(ZK_NODE, false, stat);
        log.info("修改后: {}",new String(dataAfter));
    }

    public static void main(String[] args) throws Exception {
        ZookeeperClientTest zookeeperClientTest = new ZookeeperClientTest();
        zookeeperClientTest.init();
        zookeeperClientTest.createTest();
    }
}

```

### 2.**Curator**

```xml
<dependency>
    <!-- https://mvnrepository.com/artifact/org.apache.curator/curator-recipes -->
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-recipes</artifactId>
    <version>5.0.0</version>
    <exclusions>
        <exclusion>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

```java
@Slf4j
public class CuratorClientTest {
    public static void main(String[] args) throws Exception{
        //new CuratorClientTest().testCreate();
        //new CuratorClientTest().testCreateWithParent();
        new CuratorClientTest().testGetData();
    }

    public CuratorFramework getcliet() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.148.129:2181", retryPolicy);
        client.start();
        return client;
    }

    public CuratorFramework getcliet2() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("192.168.148.129:2181")
                .sessionTimeoutMs(5000) // 会话超时时间
                .connectionTimeoutMs(5000) // 连接超时时间
                .retryPolicy(retryPolicy)
                //.namespace("base") // 包含隔离名称
                .build();
        client.start();
        return client;
    }

    public void testCreate() throws Exception {
        String path = getcliet().create().forPath("/curator‐node");
        log.info("curator create node :{} successfully.", path);
    }

    public void testCreateWithParent() throws Exception {
        String pathWithParent="/node‐parent/sub‐node‐1";
        String path = getcliet().create().creatingParentsIfNeeded().forPath(pathWithParent);
        log.info("curator create node :{} successfully.",path);
    }
    public void testGetData() throws Exception {
        byte[] bytes = getcliet().getData().forPath("/curator‐node");
        log.info("get data from node :{} successfully.",new String(bytes));
    }

    public void testSetData() throws Exception {
        getcliet().setData().forPath("/curator‐node","changed!".getBytes());
        byte[] bytes = getcliet().getData().forPath("/curator‐node");
        log.info("get data from node /curator‐node :{} successfully.",new String(bytes));
    }

    public void testDelete() throws Exception {
        String pathWithParent="/node‐parent";
        getcliet().delete().guaranteed().deletingChildrenIfNeeded().forPath(pathWithParent);
    }
}
```

