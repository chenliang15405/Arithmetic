package com.arithmetic.features.thread;

import org.junit.Test;

import java.util.Vector;

/**
 * Vector、HashTable等一定线程安全吗
 * <p>
 * 相对线程安全：可能由于线程顺序的不一致导致线程不安全
 * <p>
 * 集合的线程安全说的是该容器的操作方法是线程安全，但是在多线程中的环境下，可能由于其他的条件导致线程不安全
 *
 * @author alan.chen
 * @date 2020/7/31 11:17 PM
 */
public class VectorDemo {

    private static Vector<Integer> vector = new Vector<>();

    /**
     * Vector该容器是线程安全，但是在多线程的环境下就不一定是线程安全
     */
    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            vector.add(i);
        }
        // java.lang.ArrayIndexOutOfBoundsException: Array index out of range:
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                vector.remove(i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(vector.get(i));
            }
        }).start();

    }

}
