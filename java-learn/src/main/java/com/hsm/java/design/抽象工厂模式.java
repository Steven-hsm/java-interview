package com.hsm.java.design;

/**
 * @Classname 抽象工厂模式
 * @Description TODO
 * @Date 2021/9/24 16:02
 * @Created by huangsm
 */
public class 抽象工厂模式 {
    public static void main(String[] args) {
        AbstractFactory factory = FactoryProducer.getFactory(AbShapeFactory.class);
        AbShape shape = factory.getShape(AbRectangle.class);
        shape.draw();
    }
}
interface AbShape {
    void draw();
}
class AbRectangle implements AbShape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
class AbSquare implements AbShape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}

class AbCircle implements AbShape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}

interface Color {
    void fill();
}

class Red implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Red::fill() method.");
    }
}

class Green implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Green::fill() method.");
    }
}
class Blue implements Color {
    @Override
    public void fill() {
        System.out.println("Inside Blue::fill() method.");
    }
}

abstract class AbstractFactory {
    public abstract Color getColor(Class clazz);
    public abstract AbShape getShape(Class clazz) ;
}

class AbShapeFactory extends AbstractFactory {

    @Override
    public AbShape getShape(Class clazz) {
        if (clazz.isAssignableFrom(AbRectangle.class)) {
            return new AbRectangle();
        } else if (clazz.isAssignableFrom(AbSquare.class)) {
            return new AbSquare();
        } else if (clazz.isAssignableFrom(AbCircle.class)) {
            return new AbCircle();
        }
        return null;
    }

    @Override
    public Color getColor(Class clazz) {
        return null;
    }
}
class ColorFactory extends AbstractFactory {

    @Override
    public AbShape getShape(Class clazz){
        return null;
    }

    @Override
    public Color getColor(Class clazz) {
        if(clazz.isAssignableFrom(Red.class)){
            return new Red();
        } else if(clazz.isAssignableFrom(Green.class)){
            return new Green();
        } else if(clazz.isAssignableFrom(Blue.class)){
            return new Blue();
        }
        return null;
    }
}
class FactoryProducer {
    public static AbstractFactory getFactory(Class clazz){
        if(clazz.isAssignableFrom(AbShapeFactory.class)){
            return new AbShapeFactory();
        } else if(clazz.isAssignableFrom(ColorFactory.class)){
            return new ColorFactory();
        }
        return null;
    }
}