package com.hsm.zookeeper;

import com.sun.org.apache.xml.internal.security.Init;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
