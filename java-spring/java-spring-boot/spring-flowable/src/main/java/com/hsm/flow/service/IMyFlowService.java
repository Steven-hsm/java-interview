package com.hsm.flow.service;

import org.flowable.engine.repository.Deployment;
import org.flowable.task.api.Task;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * @Classname FlowService
 * @Description TODO
 * @Date 2021/8/17 10:03
 * @Created by huangsm
 */
public interface IMyFlowService {
    /**
     * 部署文件
     * @param name
     * @param path
     */
    boolean deployByFile(String name,String path) throws FileNotFoundException;

    /**
     * 获取已经部署文件
     */
    List<Deployment> getDeployByFile();

    /**
     * 删除部署的文件
     * @param deployIdList
     */
    void deleteDeployByFile(List<String> deployIdList);

    /**
     * 启动一个流程实例
     * @param deployId
     * @param businessKey
     * @param variables
     * @return
     */
    boolean start(String deployId, String businessKey, Map<String, Object> variables);

    /**
     * 查询任务
     */
    void queryTask(String userGroup, String username, String taskName, String businessKey);

    /**
     * 完成任务
     */
    void completeTask(String taskId,Map<String, Object> variables);

    /**
     * 根据实例id获取任务
     * @param processInstanceId
     */
    Task queryTaskByInstanceId(String processInstanceId);
}
