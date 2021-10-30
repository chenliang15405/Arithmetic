package com.github.interview;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author tangsong
 * @date 2021/9/23 12:18
 */
public class ConcurrentLRU {


    private ConcurrentHashMap<Integer, Integer> map;
    private ConcurrentLinkedQueue<Integer> queue;
    private int capacity;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    Lock readLock = readWriteLock.readLock();  // 读共享
    Lock writeLock = readWriteLock.writeLock(); // 写互斥、读写互斥

    public ConcurrentLRU(int capacity) {
        this.capacity = capacity;
        map = new ConcurrentHashMap<>();
        queue = new ConcurrentLinkedQueue<>();
    }


    public void put(Integer k, Integer v) {
        writeLock.lock();
        try {
            if(map.containsKey(k)) {
                map.put(k, v);
                queue.remove(k);
                queue.add(k);
            } else {
                map.put(k, v);
                queue.add(k);
                if(map.size() > capacity) {
                    Integer removeHead = queue.poll();
                    if(removeHead != null) {
                        map.remove(removeHead);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }
    }

    public Integer get(Integer k) {
        readLock.lock();
        try {
            if(!map.containsKey(k)) {
                return null;
            }
            Integer val = map.get(k);
            queue.remove(k);
            queue.add(k);
            return val;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            readLock.unlock();
        }
    }
}
