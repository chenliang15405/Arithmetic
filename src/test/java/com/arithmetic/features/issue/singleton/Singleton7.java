package com.arithmetic.features.issue.singleton;


/**
 * ThreadLocal实现单例模式   线程安全
 *
 * 不使用synchronized实现线程安全的单例模式
 *
 *  基于threadLocal实现的单例模式虽然是线程安全，然是失去了"单例的意义"，每个线程都有一个该对象的单例
 *
 * @author alan.chen
 * @date 2020/5/28 5:42 PM
 */
public class Singleton7 {
    private Singleton7() {}

    private static final ThreadLocal<Singleton7> THREAD_LOCAL = new ThreadLocal<Singleton7>(){
        @Override
        protected Singleton7 initialValue() {
            return new Singleton7();
        }
    };

    //11
    public static Singleton7 getInstance() {
        return THREAD_LOCAL.get();
    }

}
