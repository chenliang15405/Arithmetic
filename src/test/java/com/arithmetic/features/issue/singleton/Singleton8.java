package com.arithmetic.features.issue.singleton;

/**
 * 双重锁校验单例
 *
 * @author alan.chen
 * @date 2020/6/19 9:50 PM
 */
public class Singleton8 {

    private static volatile Singleton8 instance = null;

    private Singleton8() {
        /**
         * 防止反射破坏单例模式
         */
        if(instance != null) {
            throw new RuntimeException("not a valid method");
        }
    }

    /**
     * 防止反序列化破坏单例模式
     *
     */
    public Object readResolve() {
        return getInstance();
    }


    public static Singleton8 getInstance() {
        if(instance == null) {
            synchronized(Singleton8.class) {
                if(instance == null) {
                    instance = new Singleton8();
                }
            }
        }
        return instance;
    }

}
