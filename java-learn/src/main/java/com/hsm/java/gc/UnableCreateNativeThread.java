package com.hsm.java.gc;

import com.hsm.java.util.ThreadUtils;

public class UnableCreateNativeThread {
    public static void main(String[] args) {
        while (true){
            new Thread(()->{
                ThreadUtils.sleep(100);
            }).start();
        }
    }
}
