package com.hsm.java.design.adapter;

public class Phone {

    public  void charging(IVoltage5V voltage5V){
        if(voltage5V.output5V() == 5){
            System.out.println("电压为5V,可以充电");
        }
        System.out.println("电压不是5V,不能充电");
    }
}
