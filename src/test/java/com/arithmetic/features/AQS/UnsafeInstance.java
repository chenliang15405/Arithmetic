package com.arithmetic.features.AQS;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author alan.chen
 * @date 2020/6/13 3:39 PM
 */
public class UnsafeInstance {

    /**
     * unsafe必须通过反射获取对象
     *
     * @return
     */
    public static Unsafe reflectGetUnsafe() {
        try {
            Field fileds = Unsafe.class.getDeclaredField("theUnsafe");
            fileds.setAccessible(true);
            return (Unsafe) fileds.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
