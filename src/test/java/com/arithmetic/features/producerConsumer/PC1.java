package com.arithmetic.features.producerConsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 生产者 消费者阻塞队列 传统版
 *
 * 高并发性能表现不好，因为会频繁的释放锁和获取锁，消耗性能
 *
 * @author alan.chen
 * @date 2020/7/25 1:00 PM
 */
public class PC1<T> {

    private volatile Queue<T> queue = new LinkedList<>();

    private int max = 10;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();



    public void produce(T t) {
        lock.lock();

        try {
            /**
             * 多线程并发中判断，不能使用if，要使用while，防止虚假唤醒
             */
            while(queue.size() > 0) {
                // 生产一个数据，则等待
                condition.await();
            }
            queue.offer(t);

            System.out.println(Thread.currentThread().getName() + "\t" + t);

            condition.signalAll();
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }

    }

    public void consume() {
        lock.lock();

        try {
            while(queue.size() == 0) {
                // 没有数据则等待
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + queue.poll());

            condition.signalAll();
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
    }

}
