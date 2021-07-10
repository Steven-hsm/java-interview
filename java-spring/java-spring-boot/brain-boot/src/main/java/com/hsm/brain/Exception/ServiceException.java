package com.hsm.brain.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Classname ServerException
 * @Description TODO
 * @Date 2021/7/10 17:59
 * @Created by huangsm
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String msg;

    public ServiceException() {
        super();
    }

    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
