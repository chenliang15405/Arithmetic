package com.arithmetic.features.AQS;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 *
 *     读-读操作是共享锁，同时读取
 *     写-写/写-读操作是互斥锁，只能一个线程一个线程的更新保证原子性
 *
 *     读写锁的作用就是将读锁和写锁集成到了一个锁中，既可以实现读锁也可以使用写锁
 *     保证并发读的同时，保证写的原子性，并且写和读之间也是互斥
 *
 *
 * @author alan.chen
 * @date 2020/6/20 10:01 PM
 */
public class ReadWriteLock {


    static class MyCache {
        // volatile保证可见性，写之后立刻同步
        private volatile Map<String, Object> map = new HashMap<>();

        // 使用读写锁
        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


        /**
         * 使用写锁，保证写操作的同步和原子性，保证写-读是互斥的
         * 写操作的线程是同步的
         *
         */
        public void put(String key, String value) throws InterruptedException {
            // 使用写锁
            lock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t 开始写入: " + key);
                TimeUnit.MILLISECONDS.sleep(300);
                map.put(key, value);
                System.out.println(Thread.currentThread().getName() + "\t 写入完成 ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * 使用读锁，保证读-写是互斥，并且保证读-读是共享的，同时读
         * 并且写-读是互斥的，因为写线程先运行，所以会先进行写，那么写-读就会互斥，读操作等待写操作执行完成再读
         */
        public void get(String key) throws InterruptedException {
            // 使用读锁
            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t 开始读取");
                TimeUnit.SECONDS.sleep(1);
                Object result = map.get(key);
                System.out.println(Thread.currentThread().getName() + "\t 读取完成: " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
        }
    }


    public static void main(String[] args) {
        MyCache cache = new MyCache();

        // 写操作
        // 开启多个线程写
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    cache.put(finalI + "", finalI + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }


        // 读操作
        // 多线程读
        for (int i = 0; i < 6; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    cache.get(finalI + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

    }

}