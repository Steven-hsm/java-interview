package com.hsm.flow.callback;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @Classname SendRejectionMailCallback
 * @Description TODO
 * @Date 2021/8/18 16:12
 * @Created by huangsm
 */
public class SendRejectionMailCallback implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("拒绝了申请，给申请人发送邮件");
    }
}
