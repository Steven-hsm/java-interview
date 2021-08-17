package com.hsm.flow.callback;

import com.hsm.flow.service.IMyFlowService;
import com.hsm.flow.spring.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.IdentityService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.task.api.Task;

/**
 * @Classname AgentCallback
 * @Description TODO
 * @Date 2021/8/17 11:22
 * @Created by huangsm
 */
@Slf4j
public class AgentCallback  implements JavaDelegate {

    IMyFlowService myFlowService = SpringContextHolder.getBean(IMyFlowService.class);
    IdentityService identityService = SpringContextHolder.getBean(IdentityService.class);


    @Override
    public void execute(DelegateExecution execution) {
        String currentActivityId = execution.getCurrentActivityId();
        Task task = myFlowService.queryTaskByInstanceId(execution.getProcessInstanceId());
        String assignee = task.getAssignee();
        log.info("当前任务id:{},分配给的用户为：{}",task.getId(),assignee);
        Object var = execution.getVariable("var");
        log.info("var变量的值:{}",var);
    }


}
