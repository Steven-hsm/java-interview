package com.hsm.java.serialize.protobuf;

import lombok.Data;

/**
 * Copyright © 2017 - 2022 深圳鲲鹏云智教育科技有限公司
 * TODO
 *
 * @author steven
 * @version 1.0
 * @date 2022/3/29 10:29
 */
@Data
public class JsonData {
    private String requestId ;
    private int msgType;
    private String data;
}
