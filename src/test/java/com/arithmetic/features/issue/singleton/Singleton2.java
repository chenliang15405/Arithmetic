package com.arithmetic.features.issue.singleton;

/**
 * 懒汉模式 线程不安全
 *
 * @author alan.chen
 * @date 2020/5/28 5:36 PM
 */
public class Singleton2 {
    private Singleton2() {}

    private static Singleton2 instance;

    public static Singleton2 getInstance() {
        if(instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }


}
