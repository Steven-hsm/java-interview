package com.hsm.flow.callback;

import com.hsm.flow.service.IMyFlowService;
import com.hsm.flow.spring.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.*;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.task.api.Task;

import java.util.List;

/**
 * @Classname AgentCallback
 * @Description TODO
 * @Date 2021/8/17 11:22
 * @Created by huangsm
 */
@Slf4j
public class AgentCallback implements JavaDelegate {

    IMyFlowService myFlowService = SpringContextHolder.getBean(IMyFlowService.class);
    RepositoryService repositoryService = SpringContextHolder.getBean(RepositoryService.class);

    @Override
    public void execute(DelegateExecution execution) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(execution.getProcessDefinitionId());
        FlowNode flowNode = (FlowNode) bpmnModel.getFlowElement(execution.getCurrentActivityId());
        List<SequenceFlow> outgoingFlows = flowNode.getOutgoingFlows();
        for (SequenceFlow sequenceFlow : outgoingFlows) {
            FlowElement targetFlow = sequenceFlow.getTargetFlowElement();
            if (targetFlow instanceof UserTask) {
                System.out.println("下一节点: id=" + targetFlow.getId() + ",name=" + targetFlow.getName());
            }
            // 如果下个审批节点为结束节点
            if (targetFlow instanceof EndEvent) {
                System.out.println("下一节点为结束节点：id=" + targetFlow.getId() + ",name=" + targetFlow.getName());
            }
        }

        Task task = myFlowService.queryTaskByInstanceId(execution.getProcessInstanceId());
        String assignee = task.getAssignee();
        log.info("当前任务id:{},分配给的用户为：{}", task.getId(), assignee);
        Object var = execution.getVariable("var");

        Object description = execution.getVariable("description");
        log.info("var变量的值:{},description变量的值:{} ", var, description);
    }


}
