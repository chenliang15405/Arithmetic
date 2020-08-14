package com.arithmetic.features.issue.singleton;

/**
 * 饿汉模式  线程安全
 *
 * @author alan.chen
 * @date 2020/5/28 5:36 PM
 */
public class Singleton1 {
    private Singleton1() {}

    private static Singleton1 instance = new Singleton1();

    public static Singleton1 getInstance() {
        return instance;
    }


}
