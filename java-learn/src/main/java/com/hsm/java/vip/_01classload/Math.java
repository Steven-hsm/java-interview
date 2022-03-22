package com.hsm.java.vip._01classload;

/**
 * @author steven
 * @version 1.0
 * @date 2022/3/11 9:50
 */
public class Math {

    public static final int initData = 666;
    //public static User user = new User();

    public int compute() { //一个方法对应一块栈帧内存区域
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) {
        Math math = new Math();
        math.compute();
    }
}
//加载、验证、准备、解析、初始化、使用、卸载
//加载:在硬盘中查找并通过IO读入字节码文件

