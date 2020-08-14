package com.arithmetic.features.producerConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 生产者 消费者阻塞队列 传统版
 *
 * 一个线程对变量+1，一个线程对变量-1
 *
 * @author alan.chen
 * @date 2020/7/25 1:00 PM
 */
public class PC2<T> {

    private volatile int i = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();


    /**
     * 生产者，每次生产一个数据，然后等待消费者消费数据
     */
    public void produce() {
        lock.lock();

        try {
            // 防止线程一直再生成，消费者没有消费
            while (i != 0) {
                condition.await();
            }
            i++;
            System.out.println(Thread.currentThread().getName() + "\t" + i);
            condition.signalAll();
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }

    }

    /**
     * 消费者，每次消费一个数据，然后等待生产者生产数据
     */
    public void consume() {
        lock.lock();

        try {
            // 防止线程一直再消费，生产者没有生产消息
            while (i == 0) {
                condition.await();
            }
            i--;
            System.out.println(Thread.currentThread().getName() + "\t" + i);

            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
