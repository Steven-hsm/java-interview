package com.hsm.java.fx;

import lombok.Data;

/**
 * @Classname Person
 * @Description TODO
 * @Date 2021/8/19 20:34
 * @Created by huangsm
 */
@Data
public class Person {


    private String firstName = null;
    private String lastName = null;

    private String category = null;

    private boolean isXyz = true;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName, String category, boolean isXyz) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.category  = category;
        this.isXyz     = isXyz;
    }

}
