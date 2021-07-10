package com.hsm.brain.model.common;

import lombok.Data;

/**
 * @Classname Result
 * @Description 响应结果
 * @Date 2021/7/10 16:38
 * @Created by huangsm
 */
@Data
public class Result<T> {
    private boolean success = false;
    private T data;
    private Integer code = 400;
    private String errorMsg = "";

    public static Result error(String errorMsg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setErrorMsg(errorMsg);
        result.setCode(500);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(200);
        return result;
    }

    public static Result success() {
        return success("请求处理成功");
    }
}
