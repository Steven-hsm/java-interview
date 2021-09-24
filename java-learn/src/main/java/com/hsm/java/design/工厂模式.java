package com.hsm.java.design;

/**
 * @Classname 工厂模式
 * @Description TODO
 * @Date 2021/9/24 15:53
 * @Created by huangsm
 */
public class 工厂模式 {
    public static void main(String[] args) {
        Shape shape = ShapeFactory.getShape(Rectangle.class);
        shape.draw();
    }
}

interface Shape {
    void draw();
}

class ShapeFactory {
    public static Shape getShape(Class clazz) {
        if (clazz.isAssignableFrom(Rectangle.class)) {
            return new Rectangle();
        } else if (clazz.isAssignableFrom(Square.class)) {
            return new Square();
        } else if (clazz.isAssignableFrom(Circle.class)) {
            return new Circle();
        }
        return null;
    }
}

class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}

class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}

class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}