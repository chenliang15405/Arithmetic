package com.arithmetic.features.AQS;

import sun.misc.Unsafe;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * 实现AQS锁（这个是公平锁）
 *
 * AQS 是基于CAS和同步队列实现的锁机制
 *
 *   CAS也可以实现自旋锁（CAS本身不是锁，而是通过CAS这个机制可以来实现锁），
 *   不过没有AQS这样的可实现可重入锁和使用同步队列将线程阻塞加入队列中等待，只能一直
 *   处于自旋中，占用CPU性能。
 *
 * @author alan.chen
 * @date 2020/6/13 12:03 PM
 */
public class AqsLock {

    /**
     * 当前加锁的状态，记录加锁的次数
     */
    private volatile int state = 0;

    /**
     * 当前持有锁的线程
     */
    private Thread lockHolder;

    /**
     * 同步队列
     */
    private ConcurrentLinkedQueue<Thread> queue = new ConcurrentLinkedQueue<>();


    /**
     * CAS操作获取锁
     * @return
     */
    public boolean acquire() {
        Thread current = Thread.currentThread();

        int state = getState();
        // 如果state = 0,则尝试获取锁
        if(state == 0) {
            // 通过CAS原子操作进行加锁
            // 如果需要设置公平锁，则加锁时判断同步队列是否有数据，或者判断加锁的当前线程是否等于队列头部线程
            if((queue.size() == 0 || current == queue.peek()) && compareAndSwapState(0, 1)) {
                // 记录加锁的线程
                setLockHolder(current);
                return true;
            }
        }
        return false;
    }


    /**
     * 加锁
     */
    public void lock() {
        // 尝试获取锁
        if(acquire()) {
           return;
        }
        Thread current = Thread.currentThread();
        // 如果没有获取到锁，则加入到同步队列
        queue.add(current);

        // 自旋获取锁
        for(;;) {
            // 自旋时尝试获取锁
            // 实现公平锁，所以需要判断当前线程是否是队列头部线程
            if(current == queue.peek() && acquire()) {
                // 如果加锁成功，则移除队列
                queue.poll();
                break;
            }
            // 如果自旋没有获取到锁，则需要释放CPU资源，否则一直占用CPU资源
            // 让线程阻塞，并加入同步队列
            // 不使用yield或者sleep，因为yield会再次进入就绪状态抢夺CPU资源，sleep无法确定时间
            // 使用LockSupport的park可以指定线程阻塞
            // LockSupport.unpark会唤醒指定锁，会继续循环获取锁
            LockSupport.park(current);
        }
    }


    /**
     * 释放锁
     */
    public void unlock() {
        // 判断释放锁的对象是否是加锁的对象
        if(Thread.currentThread() != lockHolder) {
            throw new RuntimeException("lockholder is not current thread");
        }

        // 释放锁
        int state = getState();
        if(compareAndSwapState(state, 0)) {
            setLockHolder(null);
            // 唤醒队列头部的锁
            Thread peek = queue.peek();
            if(peek != null) {
                LockSupport.unpark(peek);
            }
        }
    }

    /**
     * 原子操作修改state
     * @param except
     * @param update
     * @return
     */
    public final boolean compareAndSwapState(int except, int update) {
        // CAS进行修改
        return unsafe.compareAndSwapInt(this, stateOffset, except, update);
    }

    private static final Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();

    private static long stateOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(AqsLock.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Thread getLockHolder() {
        return lockHolder;
    }

    public void setLockHolder(Thread lockHolder) {
        this.lockHolder = lockHolder;
    }
}
