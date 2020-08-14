package com.arithmetic.features.thread;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author alan.chen
 * @date 2020/7/25 1:14 PM
 */
public class OddEvenNum {

    private volatile int i;

    private volatile int j;

    /**
     * 多线程交替打印奇偶数
     */
    @Test
    public void test2() throws InterruptedException {

        new Thread(() -> {
            synchronized (this) {
                for (int i = 1; i <= 10; i+=2) {
                    System.out.println("---" + i);
                    this.notifyAll();
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (this) {
                for (int i = 2; i <= 10; i+=2) {
                    System.out.println("****" + i);
                    this.notifyAll();
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }


    @Test
    public void test() {
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 1; i <= 10; i += 2) {
                    printNumber(i);
                }
            }
        });

        t1.setName("Thread t1");
        t1.start();

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 2; i <= 10; i += 2) {
                    printNumber(i);
                }
            }
        });

        t2.setName("Thread t2");
        t2.start();
    }


    @Test
    public void test3() {
        ReentrantLock rl = new ReentrantLock();
        Condition condition = rl.newCondition();

        new Thread(() -> {
            rl.lock();
            for (int k = 1; k < 10; k += 2) {
                System.out.println("t1  " + k);
                condition.signal();
                try {
                    // 使当前线程加入 await() 等待队列中，并释放当锁，当其他线程调用signal()会重新请求锁。与Object.wait()类似。
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
            rl.unlock();
        }).start();

        new Thread(() -> {
            rl.lock();
            for (int k = 2; k < 10; k += 2) {
                System.out.println("t2  " + k);
                condition.signal();
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }
            rl.unlock();
        }).start();

    }

    @Test
    public void test5() {
        new Thread(() -> {
            synchronized (this) {
                for (int k = 1; k < 10; k += 2) {
                    System.out.println("t1 " + k);
                    this.notifyAll();
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (this) {
                for (int k = 2; k < 10; k += 2) {
                    System.out.println("t2 " + k);
                    this.notifyAll();
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();

    }


    private ReentrantLock rl = new ReentrantLock();
    private Condition condition = rl.newCondition();

    public void printNumber(int i) {
        try {
            rl.lock();
            System.out.println(Thread.currentThread().getName() + " " + i);
            condition.signal();
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rl.unlock();
        }
    }

}
