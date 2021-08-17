package com.hsm.flow.service.impl;

import com.alibaba.fastjson.JSON;
import com.hsm.flow.service.IMyFlowService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * @Classname FlowService
 * @Description TODO
 * @Date 2021/8/17 10:03
 * @Created by huangsm
 */
@Service
@Slf4j
public class MyFlowService implements IMyFlowService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Override
    public boolean deployByFile(String name, String path) throws FileNotFoundException {
        Deployment 资源名称 = repositoryService
                .createDeployment()
                .addClasspathResource(path)
                .name(name)
                .deploy();
        return true;
    }

    @Override
    public List<Deployment> getDeployByFile() {
        List<Deployment> list = repositoryService
                .createDeploymentQuery().list();
        return  list;
    }

    @Override
    public void deleteDeployByFile(List<String> deployIdList) {
        deployIdList.forEach(deployId ->{
            repositoryService.deleteDeployment(deployId);
        });
    }

    @Override
    public boolean start(String deployId, String businessKey, Map<String, Object> variables) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployId)
                .singleResult();
        if(processDefinition == null){
            log.error("没有找到对应的流程");
            return false;
        }
        runtimeService.startProcessInstanceByKey(processDefinition.getKey(),
                businessKey,variables);
        return true;
    }

    @Override
    public void queryTask(String userGroup, String username, String taskName, String businessKey) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        if(StringUtils.hasText(taskName)) {
            taskQuery.taskNameLikeIgnoreCase("%"+taskName.trim()+"%");
        }
        if(StringUtils.hasText(businessKey)) {
            taskQuery.processInstanceBusinessKeyLikeIgnoreCase("%"+businessKey.trim()+"%");
        }
        if(StringUtils.hasText(userGroup)) {
            taskQuery.taskCandidateGroup(userGroup.trim());
        }
        if(StringUtils.hasText(username)) {
            taskQuery.taskAssigneeLikeIgnoreCase(username.trim());
        }
        List<Task> list = taskQuery.list();
        list.forEach(task->{
            log.info("任务id:{},任务名称:{}",task.getId(),task.getName());
        });
    }

    @Override
    public void completeTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId,variables);
    }

    @Override
    public Task queryTaskByInstanceId(String processInstanceId) {
        List<Task> list = taskService
                .createTaskQuery()
                .processInstanceId(processInstanceId)
                .list();
        if(!CollectionUtils.isEmpty(list)){
            return  list.get(0);
        }
        return null;
    }
}
