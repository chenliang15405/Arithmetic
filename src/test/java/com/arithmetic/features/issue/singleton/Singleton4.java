package com.arithmetic.features.issue.singleton;

/**
 * 饿汉模式变种   线程安全
 *
 * 通过类加载保证线程安全
 *
 * @author alan.chen
 * @date 2020/5/28 5:42 PM
 */
public class Singleton4 {
    private Singleton4() {}

    private static Singleton4 instance;

    static {
        instance = new Singleton4();
    }


    public static Singleton4 getInstance() {
        return instance;
    }

}
