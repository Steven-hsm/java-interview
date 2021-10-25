package com.hsm.java.design.adapter;

public class Client {
    public static void main(String[] args) {
        Phone phone = new Phone();
        //类适配器
        phone.charging(new VoltageAdapter());
        //对象适配器
        phone.charging(new VoltageAdapter2(new Voltage220V()));


    }
}

