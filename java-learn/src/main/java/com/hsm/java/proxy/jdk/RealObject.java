package com.hsm.java.proxy.jdk;

public class RealObject implements InterfaceObject{
    @Override
    public void sayhello() {
        System.out.println("say hello");
    }
}
