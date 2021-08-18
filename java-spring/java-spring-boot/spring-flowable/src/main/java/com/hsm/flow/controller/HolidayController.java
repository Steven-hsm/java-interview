package com.hsm.flow.controller;

import com.hsm.flow.entity.TaskResp;
import com.hsm.flow.service.IHolidayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname HolidayController
 * @Description 请假流程申请
 * @Date 2021/8/18 15:19
 * @Created by huangsm
 */
@RestController
@Api(tags = "请求流程控制器", value = "请求流程控制器")
@RequestMapping("/holiday")
@Slf4j
public class HolidayController {
    @Autowired
    private IHolidayService holidayService;

    @PostMapping("create")
    @ApiOperation(value = "创建请假申请", notes = "创建请假申请")
    public boolean createRequest(@RequestParam("userName") String userName,
                                 @RequestParam("reason") String reason,
                                 @RequestParam("days") Integer days){
        return holidayService.createRequest(userName,reason,days);
    }

    @GetMapping("list/task")
    @ApiOperation(value = "任务列表查询", notes = "任务列表查询")
    public List<TaskResp> listTask(@RequestParam(value = "userGroup",required = false) String userGroup,
                                   @RequestParam(value = "username",required = false) String username,
                                   @RequestParam(value = "taskName",required = false) String taskName,
                                   @RequestParam(value = "businessKey",required = false) String businessKey){
        return holidayService.listTask(userGroup,username,taskName,businessKey);
    }

    @GetMapping("complete/task")
    @ApiOperation(value = "审核任务", notes = "审核任务")
    public boolean completeTask(@RequestParam(value = "taskId",required = true) String taskId,
                                   @RequestParam(value = "approved",required = true) boolean approved){
        return holidayService.completeTask(taskId,approved);
    }
}
