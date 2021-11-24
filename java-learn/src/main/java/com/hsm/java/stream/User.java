package com.hsm.java.stream;

import lombok.Data;

@Data
public class User {
    private String userName;//用户名
    private Integer age;//年纪

    public User(String userName, Integer age) {
        this.userName = userName;
        this.age = age;
    }
}
