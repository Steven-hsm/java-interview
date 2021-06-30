package com.hsm.transposition;

import lombok.Data;

/**
 * @Classname User
 * @Description TODO
 * @Date 2021/6/30 9:14
 * @Created by huangsm
 */
@Data
public class User {
    private int userId;
    private String userName;
    private int age;

    public User(int userId, String userName, int age) {
        this.userId = userId;
        this.userName = userName;
        this.age = age;
    }
}
