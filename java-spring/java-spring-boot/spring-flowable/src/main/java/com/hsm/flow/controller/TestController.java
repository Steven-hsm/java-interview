package com.hsm.flow.controller;

import com.hsm.flow.service.IMyFlowService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/8/17 10:02
 * @Created by huangsm
 */
@RestController
@Api(tags = "工作流测试", value = "工作流测试")
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private IMyFlowService myFlowService;

    @PostMapping("deployByFile")
    public void deployByFile() throws FileNotFoundException {
        myFlowService.deployByFile("代理加盟申请","processes\\agent-apply.bpmn20.xml");
    }
    // 查询部署流程
    @PostMapping("getDeployByFile")
    public void getDeployByFile() throws FileNotFoundException {
        List<Deployment> deployByFile = myFlowService.getDeployByFile();
        log.info("已经部署的流程：{}" , deployByFile.toString());
    }

    // 删除部署流程
    @PostMapping("deleteDeployByFile")
    public void deleteDeployByFile() throws FileNotFoundException {
        List<String> deployIdList = Arrays.asList("8a2dfdd9-ff23-11eb-8d0f-00ff00647775","7");
        myFlowService.deleteDeployByFile(deployIdList);
    }

    //部署一个工作流实例
    @PostMapping("startFlow")
    public void startFlow() throws FileNotFoundException {
        Map<String, Object> variables = new HashMap<>();

        variables.put("employee", "张三");
        variables.put("nrOfHolidays", 3);
        variables.put("description", "张三请假申请");
        String businessKey = "test";
        boolean start = myFlowService.start("15001",businessKey, variables);
    }

    //查询任务
    @PostMapping("queryTask")
    public void queryTask() throws FileNotFoundException {
        myFlowService.queryTask(null,null,null,"test");
    }

    //查询任务
    @PostMapping("queryTaskByInstanceId")
    public void queryTaskByInstanceId() throws FileNotFoundException {
        myFlowService.queryTaskByInstanceId("123131");
    }

    //完成任务
    @PostMapping("CompleteTask")
    public void CompleteTask() throws FileNotFoundException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved",false);
        variables.put("var",123);
        myFlowService.completeTask("15045",variables);
    }
}
