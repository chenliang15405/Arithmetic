package com.arithmetic.features.thread;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author alan.chen
 * @date 2020/5/20 6:23 PM
 */
public class CountDownLatchTest {


    /**
     * CountDownLatch是通过多线程中调用countDown的方式减计数，多线程中调用countDown后继续执行，
     * 主线程等待，等待计数等于0的时候，等待的线程开始执行
     *
     * 一个线程或多个线程调用await的线程会阻塞，调用countDown的线程不会阻塞，当countDown将计数器减到0，则阻塞的线程
     * 会被唤醒开始运行
     *
     *  CountDownLatch只会使用一次
     */
    @Test
    public void test1() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            int index = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(index * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println("第" + index + "位执行ok!");
            });
        }

        latch.await();

        System.out.println("一起执行");
    }

    /**
     * CyclicBarrier 是通过多线程中让每个线程等待，当所有的线程都处于等待的时候，同时开始执行所有线程
     *
     * CyclicBarrier可以重复使用
     *
     */
    @Test
    public void test2() throws InterruptedException, BrokenBarrierException {
        ExecutorService executor = Executors.newCachedThreadPool();
        CyclicBarrier barrier = new CyclicBarrier(3);

        for (int i = 0; i < 3; i++) {
            int index = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(index * 1000);
                    System.out.println(index + " 等待");
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("第" + index + "位执行ok!");
            });
        }

        while (true) {

        }
        //System.out.println("一起执行");
    }


    /**
     * CyclicBarrier 通过构造方法可以定义到达等待计数时执行的任务线程
     */
    @Test
    public void test3() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            // 定义线程，当所有计数的线程达到await，则执行此线程，和通过await手动阻塞基本一致
            System.out.println("*****召唤神龙");
        });

        for (int i = 1; i <= 3; i++) {
            final int tempInt = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t集齐第" + tempInt + "龙珠");
                // 调用await
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }


    /**
     * semaphore信号量，控制同步资源的并发数
     * @param args
     */
    public static void main(String[] args) {
        // semaphore还可以设置公平锁和非公平锁，在构造方法中设置
        // 定义semaphore的信号量，一个线程获取一个信号量，则最多允许3个线程同时进入锁的代码中执行
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {

                try {
                    // 加锁，同时可以进入3个线程
                    semaphore.acquire();
                    //semaphore.acquire(3); // 也可以同时设置一个线程获取多个信号锁，这样相当于一个线程获取了所有的信号锁，其他的线程只能等有足够多信号锁才可以获取到锁

                    System.out.println(Thread.currentThread().getName() + "\t抢到车位");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "\t停车3秒后离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放锁
                    semaphore.release();
                    //semaphore.release(3); // 可以设置同时释放多个锁
                }

            }, String.valueOf(i)).start();
        }

    }


}
