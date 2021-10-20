package com.hsm.java.design;

/**
 * @Classname 建造者模式
 * @Description TODO
 * @Date 2021/9/24 16:49
 * @Created by huangsm
 */
public class 建造者模式 {
    public static void main(String[] args) {

    }


}
interface Packing {
    public String pack();
}

interface Item {
    public String name();
    public Packing packing();
    public float price();
}

class Wrapper implements Packing {
    @Override
    public String pack() {
        return "Wrapper";
    }
}
class Bottle implements Packing {

    @Override
    public String pack() {
        return "Bottle";
    }
}

abstract class Burger implements Item {
    @Override
    public Packing packing() {
        return new Wrapper();
    }
    @Override
    public abstract float price();
}
abstract class ColdDrink implements Item {

    @Override
    public Packing packing() {
        return new Bottle();
    }

    @Override
    public abstract float price();
}

class VegBurger extends Burger {

    @Override
    public float price() {
        return 25.0f;
    }

    @Override
    public String name() {
        return "Veg Burger";
    }
}

class ChickenBurger extends Burger {

    @Override
    public float price() {
        return 50.5f;
    }

    @Override
    public String name() {
        return "Chicken Burger";
    }
}
class Coke extends ColdDrink {

    @Override
    public float price() {
        return 30.0f;
    }

    @Override
    public String name() {
        return "Coke";
    }
}
class Pepsi extends ColdDrink {

    @Override
    public float price() {
        return 35.0f;
    }

    @Override
    public String name() {
        return "Pepsi";
    }
}