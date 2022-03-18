package com.hsm.java._01classloader;

/**
 * Copyright © 2017 - 2022 深圳鲲鹏云智教育科技有限公司
 * TODO
 *
 * @author steven
 * @version 1.0
 * @date 2022/1/18 9:56
 */
public class Math {
    public static final int initData = 666;

    /**
     * 一个方法对应一块栈帧内存区域
     */
    public int compute() {
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        Math math = new Math();
        math.compute();
    }

    //加载：读取字节码
    //验证：校验字节码文件的准确性
    //准备：给类的静态变量分配内存，并赋予默认值
    //解析：将符号引用转为直接引用
    //初始化：对类的静态变量初始化为指定的值，执行静态代码块
    //使用
    //卸载

    //类加载到方法区中后主要包含 运行时常量池、类型信息、字段信息、方法信息、类加载器的引用、对应class实例的引用

}
