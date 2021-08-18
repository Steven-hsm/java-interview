package com.hsm.flow.service;

import com.hsm.flow.entity.TaskResp;

import java.util.List;

/**
 * @Classname IHolidayService
 * @Description 请求服务
 * @Date 2021/8/18 15:23
 * @Created by huangsm
 */
public interface IHolidayService {
    /**
     * 创建请假请求
     * @param userName
     * @param reason
     * @param days
     * @return
     */
    boolean createRequest(String userName, String reason, Integer days);

    /**
     * 获取任务列表
     * @param userGroup
     * @param username
     * @param taskName
     * @param businessKey
     * @return
     */
    List<TaskResp> listTask(String userGroup, String username, String taskName, String businessKey);

    /**
     * 完成任务
     * @param taskId
     * @param approved
     * @return
     */
    boolean completeTask(String taskId, boolean approved);
}
