package com.github.interview;

import jdk.nashorn.internal.ir.Block;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tangsong
 * @date 2021/9/18 21:56
 */
public class BlockingQueue {
    private volatile int[] arr;
    private volatile int capacity;
    private volatile int index;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public BlockingQueue(int size) {
        this.capacity = size;
        arr = new int[size];
    }

    public void put(int num) {
        lock.lock();
        try {
            while(index == this.capacity) {
                condition.await();
            }
            arr[index++] = num;
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public int take() {
        lock.lock();
        try {
            while(this.index == 0) {
                condition.await();
            }
            condition.signalAll();
            return arr[--index];
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BlockingQueue queue = new BlockingQueue(10);
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    queue.put(j);
                }
            }).start();
        }

        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                for (int j = 0; j < 20; j++) {
                    System.out.println(queue.take());
                }
            }).start();
        }

    }

}
