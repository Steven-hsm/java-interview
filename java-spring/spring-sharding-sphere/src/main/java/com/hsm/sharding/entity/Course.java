package com.hsm.sharding.entity;

import lombok.Data;

/**
 * @author ：楼兰
 * @date ：Created in 2021/1/4
 * @description:
 **/
@Data
public class Course {

    private Long cid;
    private String cname;
    private Long userId;
    private String cstatus;

    @Override
    public String toString() {
        return "Course{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", userId=" + userId +
                ", cstatus='" + cstatus + '\'' +
                '}';
    }
}
