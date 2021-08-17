package com.hsm.flow.service.impl;

import com.alibaba.fastjson.JSON;
import com.hsm.flow.WebApplication;
import com.hsm.flow.service.IMyFlowService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.repository.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Classname FlowServiceTest
 * @Description TODO
 * @Date 2021/8/17 10:08
 * @Created by huangsm
 */
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class FlowServiceTest {
    @Autowired
    private IMyFlowService myFlowService;
    // 部署流程文件
    @Test
    public void deployByFile() throws FileNotFoundException {
        myFlowService.deployByFile("代理加盟申请","E:\\github\\java-interview\\java-spring\\java-spring-boot\\spring-flowable\\src\\main\\resources\\processes\\agent-apply.bpmn20.xml");
    }

    // 查询部署流程
    @Test
    public void getDeployByFile() throws FileNotFoundException {
        List<Deployment> deployByFile = myFlowService.getDeployByFile();
        log.info("已经部署的流程：{}" , deployByFile.toString());
    }

    // 删除部署流程
    @Test
    public void deleteDeployByFile() throws FileNotFoundException {
        List<String> deployIdList = Arrays.asList("080784cc-ff02-11eb-9bf1-00ff00647775","1","5534f1d0-ff03-11eb-bde1-00ff00647775");
        myFlowService.deleteDeployByFile(deployIdList);
    }

    //部署一个工作流实例
    @Test
    public void startFlow() throws FileNotFoundException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", "张三");
        variables.put("nrOfHolidays", 3);
        variables.put("description", "张三请假申请");
        String businessKey = "test";
        boolean start = myFlowService.start("3caaf054-ff0f-11eb-8f57-00ff00647775",businessKey, variables);
    }


}