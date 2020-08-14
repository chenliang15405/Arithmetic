package com.arithmetic.features.pattern.decorator;

/**
 * @author alan.chen
 * @date 2020/7/22 10:58 PM
 */
public class DecoratorPattern extends Decorator{

    public DecoratorPattern(Food food) {
        super(food);
    }

    public String eat() {
        return super.eat() + "薯条";
    }

}
