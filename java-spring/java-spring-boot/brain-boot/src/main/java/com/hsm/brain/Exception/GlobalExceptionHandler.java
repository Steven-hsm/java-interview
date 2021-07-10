package com.hsm.brain.Exception;

import ch.qos.logback.core.status.ErrorStatus;
import com.hsm.brain.model.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname ExceptionHandler
 * @Description TODO
 * @Date 2021/7/10 16:37
 * @Created by huangsm
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(RuntimeException e) {
        log.error("exceptionHandler Error:{}", e);
        return Result.error("服务器内部异常");
    }
}
