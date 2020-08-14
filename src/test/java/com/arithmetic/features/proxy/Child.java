package com.arithmetic.features.proxy;

/**
 * @author alan.chen
 * @date 2020/7/13 6:18 PM
 */
public class Child implements P {


    @MyTransaction(value = "1", timeout = 30)
    @Override
    public int add() {
        System.out.println("目标方法---add");
        return 100;
    }

    @Override
    public void print() {
        System.out.println("目标方法---print");
    }
}
