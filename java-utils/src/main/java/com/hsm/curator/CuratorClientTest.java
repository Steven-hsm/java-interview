package com.hsm.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

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
