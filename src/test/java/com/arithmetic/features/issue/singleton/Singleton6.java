package com.arithmetic.features.issue.singleton;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS实现单例模式   线程安全
 *
 * 不使用synchronized实现线程安全的单例模式
 *
 * @author alan.chen
 * @date 2020/5/28 5:42 PM
 */
public class Singleton6 {
    private Singleton6() {}

    // AtomicReference 原子引用类
    private static final AtomicReference<Singleton6> REFERENCE = new AtomicReference<>();


    public static Singleton6 getInstance() {
        while (true) {
            if(REFERENCE.get() != null) {
                return REFERENCE.get();
            }
            Singleton6 instance = new Singleton6();
            // 通过CAS赋值
            // 第一个参数为内存中的预期值，第二个参数为要更新的值
            if(REFERENCE.compareAndSet(null, instance)) {
                return instance;
            }
        }
    }

}
