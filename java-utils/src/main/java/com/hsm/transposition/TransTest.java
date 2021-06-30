package com.hsm.transposition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Classname TransTest
 * @Description TODO
 * @Date 2021/6/30 9:14
 * @Created by huangsm
 */
public class TransTest {
    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "张三", 10));
        userList.add(new User(2, "李四", 11));
        userList.add(new User(1, "张三", 10));
        userList.add(new User(2, "李四", 11));
        userList.add(new User(1, "张三", 10));
        userList.add(new User(2, "李四", 11));
        userList.add(new User(1, "张三", 10));
        userList.add(new User(2, "李四", 11));
        //转置前的原对象
        userList.forEach(System.out::println);
        //需要转置的对象属性
        List<String> fieldStr = Arrays.asList("userId", "userName", "age");
        //转置
        String[][] strArray = TransUtils.convertToArray(userList, fieldStr);
        //输出转置后的对象
        for (int i = 0; i < strArray.length; i++) {
            for (int j = 0; j < strArray[i].length; j++) {
                System.out.print(strArray[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
