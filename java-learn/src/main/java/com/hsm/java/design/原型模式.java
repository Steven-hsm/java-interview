package com.hsm.java.design;

/**
 * @Classname 原型模式
 * @Description 一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型相同或相似的新对象。
 *
 * @Date 2021/9/24 15:21
 * @Created by huangsm
 */
public class 原型模式 {
    public static void main(String[] args) throws Exception{
        Realizetype obj1 = new Realizetype();
        Realizetype obj2 = (Realizetype) obj1.clone();
        System.out.println("obj1==obj2?" + (obj1 == obj2));
    }
}
//具体原型类
class Realizetype implements Cloneable {
    Realizetype() {
        System.out.println("具体原型创建成功！");
    }
    public Object clone() throws CloneNotSupportedException {
        System.out.println("具体原型复制成功！");
        return (Realizetype) super.clone();
    }
}