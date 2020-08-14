package com.arithmetic.features.AQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 实现自旋锁
 *
 *   自旋锁就是通过循环和CAS实现
 *   如果线程并发高，竞争大，可能会导致循环实现长，CPU消耗高
 *
 *
 * @author alan.chen
 * @date 2020/6/20 7:46 PM
 */
public class CASLock {

    // 使用原子int和原子引用都可以
    // AtomicInteger atomicInteger = new AtomicInteger(0);

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {

        Thread thread = Thread.currentThread();

        System.out.println(Thread.currentThread().getName() + "\t 加锁");
        // 如果没有设置成功(没有获取到锁)，则自旋
        while (!atomicReference.compareAndSet(null, thread)) {

        }
        System.out.println(Thread.currentThread().getName() + "\t 加锁成功");
    }

    public void unLock() {
        Thread thread = Thread.currentThread();

        System.out.println(Thread.currentThread().getName() + "\t 解锁");
        atomicReference.compareAndSet(thread, null);
    }


    public static void main(String[] args) throws InterruptedException {

        CASLock casLock = new CASLock();

        new Thread(() -> {
            casLock.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            casLock.unLock();
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            casLock.myLock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            casLock.unLock();
        }, "t2").start();

    }


}
