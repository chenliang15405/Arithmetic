package com.github.interview;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tangsong
 * @date 2021/9/18 21:56
 */
public class BlockingQueueCurrent {
    private volatile int[] arr;  // 这里还可以换位LinkedList
    private volatile int capacity;
    private volatile int index; // 可以使用volatile，也可以直接使用AtomicInteger

    private Lock putLock = new ReentrantLock();
    private Lock takeLock = new ReentrantLock();
    private Condition notFull = putLock.newCondition();
    private Condition notEmpty = takeLock.newCondition();

    public BlockingQueueCurrent(int size) {
        this.capacity = size;
        arr = new int[size];
    }

    /**
     * 读写并发的双重锁
     *
     *
     */
    public void put(int num) {
        putLock.lock();
        try {
            while(index == this.capacity) {
                notFull.await();
            }
            arr[index++] = num;
            if(index < capacity) {
                notFull.signalAll();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            putLock.unlock();
        }
        if(index >= capacity) {
            signalNotEmpty();
        }
    }

    public int take() {
        int x;
        takeLock.lock();
        try {
            while(this.index == 0) {
                notEmpty.await();
            }
            x = arr[--index];
            if(index >= 1) {
                notEmpty.signalAll();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            takeLock.unlock();
        }
        if(index == 0) {
            signalNotFull();
        }
        return x;
    }

    private void signalNotFull() {
        this.putLock.lock();
        notFull.signalAll();
        this.putLock.unlock();
    }

    private void signalNotEmpty() {
        this.takeLock.lock();
        notEmpty.signalAll();
        this.takeLock.unlock();
    }


    public static void main(String[] args) {
        BlockingQueueCurrent queue = new BlockingQueueCurrent(10);
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
