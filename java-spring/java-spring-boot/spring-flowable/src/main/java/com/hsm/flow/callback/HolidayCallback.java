package com.hsm.flow.callback;

import com.hsm.flow.entity.TaskResp;
import com.hsm.flow.service.IHolidayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname HolidayController
 * @Description 请假流程申请
 * @Date 2021/8/18 15:19
 * @Created by huangsm
 */
public class HolidayCallback  implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("审核通过了，保存假期数据入库");
    }
}
