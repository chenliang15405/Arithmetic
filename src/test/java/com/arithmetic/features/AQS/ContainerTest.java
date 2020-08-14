package com.arithmetic.features.AQS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author alan.chen
 * @date 2020/6/25 10:53 AM
 */
public class ContainerTest {

    private volatile List<Object> list = new ArrayList<>();


    public void add(Object o) {
        list.add(o);
    }

    public int get() {
        return list.size();
    }

    static volatile boolean flag = true;


    /**
     * 定义容器，向容器中添加元素，线程1添加10个元素，当添加5个元素时，线程2发出提示
     * @param args
     */
    public static void main(String[] args) {
        ContainerTest  containerTest = new ContainerTest();

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Condition condition1 = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
            }
            System.out.println("已经到达5个元素");
            condition1.signal();
            lock.unlock();
        }, "t2").start();

        Runnable t = () -> {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                containerTest.add(i);
                System.out.println("t1  " + i);
                if(containerTest.get() == 5) {
                    condition.signal();
                    try {
                        condition1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            lock.unlock();
        };

        Thread t1 = new Thread(t);
        t1.start();


    }

    private static void method2CountDown(ContainerTest containerTest) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(1);

        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("已经到达5个元素");
            countDownLatch2.countDown();
        }, "t2").start();

        Runnable t = () -> {
            for (int i = 0; i < 10; i++) {
                containerTest.add(i);
                System.out.println("t1  " + i);
                if(containerTest.get() == 5) {
                    countDownLatch.countDown();
                    try {
                        countDownLatch2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t1 = new Thread(t);
        t1.start();
    }

    private static void method1(ContainerTest containerTest) {
        Runnable t = () -> {
            for (int i = 0; i < 10; i++) {
                containerTest.add(i);
                System.out.println("t1  " + i);
                if(containerTest.get() == 5) {
                    flag = false;
                    LockSupport.park();
                }
            }
        };

        Thread t1 = new Thread(t);
        t1.start();

        new Thread(() -> {
            while(flag) {

            }
            System.out.println("已经到达5个元素");
            LockSupport.unpark(t1);
        }, "t2").start();
    }


}
