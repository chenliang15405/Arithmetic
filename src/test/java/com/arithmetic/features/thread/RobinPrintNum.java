package com.arithmetic.features.thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author alan.chen
 * @date 2020/7/27 12:00 AM
 */
public class RobinPrintNum {


    ReentrantLock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    /**
     * 3个线程交替打印 1-75
     *
     * 需要使用3个Condition和1个相同锁对象，来控制打印的顺序
     *
     */
    @Test
    public void test() throws InterruptedException {
        new Thread(() -> {
            lock.lock();
            try {
                for (int i = 1; i <= 75; i+=3) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                    c2.signalAll();
                    c1.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "AA").start();

        new Thread(() -> {
            lock.lock();
            try {
                for (int i = 2; i <= 75; i+=3) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                    c3.signalAll();
                    c2.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "BB").start();

        new Thread(() -> {
            lock.lock();
            try {
                for (int i = 3; i <= 75; i+=3) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                    c1.signalAll();
                    c3.await();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "CC").start();

        // 防止main线程提前退出
        Thread.sleep(3000);
    }


    @Test
    public void test2() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        new Thread(() -> {
            lock.lock();
            try {
                while(atomicInteger.get() <= 75) {
                    System.out.println(Thread.currentThread().getName() + "\t" + atomicInteger.getAndIncrement());
                    c2.signalAll();
                    c1.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "AA").start();

        new Thread(() -> {
            lock.lock();
            try {
                while(atomicInteger.get() <= 75) {
                    System.out.println(Thread.currentThread().getName() + "\t" + atomicInteger.getAndIncrement());
                    c3.signalAll();
                    c2.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "BB").start();

        new Thread(() -> {
            lock.lock();
            try {
                while(atomicInteger.get() <= 75) {
                    System.out.println(Thread.currentThread().getName() + "\t" + atomicInteger.getAndIncrement());
                    c1.signalAll();
                    c3.await();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "CC").start();

        Thread.sleep(3000);
    }


    private Semaphore sp1 = new Semaphore(1);

    @Test
    public void test3() throws InterruptedException {
        new Thread(() -> {
            try {
                // TODO 无法控制获取锁的顺序
                sp1.acquire();
                for (int i = 1; i <= 75; i+=3) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                    sp1.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

            }
        }, "AA").start();

        new Thread(() -> {
            try {
                sp1.acquire();
                for (int i = 2; i <= 75; i+=3) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                    sp1.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }
        }, "BB").start();

        new Thread(() -> {
            try {
                sp1.acquire();
                for (int i = 3; i <= 75; i+=3) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                    sp1.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }, "CC").start();

        // 防止main线程提前退出
        Thread.sleep(3000);
    }

}
