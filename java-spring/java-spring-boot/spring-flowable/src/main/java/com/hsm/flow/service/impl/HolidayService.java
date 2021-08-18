package com.hsm.flow.service.impl;

import com.hsm.flow.entity.TaskResp;
import com.hsm.flow.service.IHolidayService;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname HolidayService
 * @Description 请求服务
 * @Date 2021/8/18 15:23
 * @Created by huangsm
 */
@Service
public class HolidayService implements IHolidayService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;


    private static final String processDefinitionId = "holiday-request";

    @Override
    public boolean createRequest(String userName, String reason, Integer days) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("userName", userName);
        variables.put("reason", reason);
        variables.put("days", days);
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(processDefinitionId, variables);
        return true;
    }

    @Override
    public List<TaskResp> listTask(String userGroup, String username, String taskName, String businessKey) {
        List<TaskResp> dataList = new ArrayList<>();
        TaskQuery taskQuery = taskService.createTaskQuery();
        if(StringUtils.isNotEmpty(username)){
            taskQuery.taskAssignee(username);
        }
        if(StringUtils.isNotEmpty(userGroup)){
            taskQuery.taskCandidateGroup(userGroup);
        }
        List<Task> list = taskQuery.list();
        for (Task task : list) {
            TaskResp taskResp = new TaskResp();
            taskResp.setTaskId(task.getId());
            taskResp.setTaskName(task.getName());
            dataList.add(taskResp);
        }
        return dataList;
    }

    @Override
    public boolean completeTask(String taskId, boolean approved) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved",approved);
        variables.put("var",123);
        taskService.complete(taskId,variables);
        return true;
    }
}
