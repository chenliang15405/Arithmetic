package com.arithmetic.features.issue.singleton;

/**
 * 静态内部类  线程安全
 *
 * 实现原理：通过classLoader的loadClass中的synchronized保证线程安全，类只会实例化一次，该对象属于类变量
 *
 * @author alan.chen
 * @date 2020/5/28 5:36 PM
 */
public class Singleton3 {
    private Singleton3() {}


    private static class SingletonInner {
        public static Singleton3 instance = new Singleton3();
    }

    public static Singleton3 getInstance() {
        return SingletonInner.instance;
    }

}
