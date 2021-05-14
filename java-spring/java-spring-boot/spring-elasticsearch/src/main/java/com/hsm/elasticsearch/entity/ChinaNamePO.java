package com.hsm.elasticsearch.entity;

import lombok.Data;
import org.zxp.esclientrhl.annotation.ESMapping;

/**
 * @description: 中文名
 * @author: huangsm
 * @createDate: 2021/3/16
 */
@Data
public class ChinaNamePO {
    @ESMapping
    private String firstName;
    @ESMapping
    private String lastName;

    public ChinaNamePO() {
    }

    public ChinaNamePO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
