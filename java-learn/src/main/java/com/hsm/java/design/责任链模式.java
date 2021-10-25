package com.hsm.java.design;

import lombok.Data;

public class 责任链模式 {
    public static void main(String[] args) {
        new Request(1, 31000, 1)
    }
}

abstract class HandlerChain {
    HandlerChain nextChain;
    String name;

    public HandlerChain(String name) {
        this.name = name;
    }

    public void setNextChain(HandlerChain nextChain) {
        this.nextChain = nextChain;
    }

    public abstract void processRequest(Request request);
}

@Data
class Request {
    //请求类型
    private int type = 0;
    //请求金额
    private float price = 0.0f;
    private int id = 0;

    public Request(int type, float price, int id) {
        this.type = type;
        this.price = price;
        this.id = id;
    }
}