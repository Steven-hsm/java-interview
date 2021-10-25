package com.hsm.java.design.adapter;

public class VoltageAdapter2 implements IVoltage5V {

    private Voltage220V voltage220V;

    public VoltageAdapter2(Voltage220V voltage220V) {
        this.voltage220V = voltage220V;
    }

    @Override
    public int output5V() {
        int dst = 0;
        if (null != voltage220V) {
            int src = voltage220V.output220V();
            dst = src / 44;
        }
        return dst;
    }
}
