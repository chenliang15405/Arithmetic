package com.arithmetic.features.issue;


import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  LinkedHashMap实现LRU算法
 *
 * @author alan.chen
 * @date 2020/5/26 3:52 PM
 */
public class LRUCache extends LinkedHashMap {

    private int capacity;

    public LRUCache(int capacity) {
        super((int)Math.ceil(capacity / 0.75) + 1, 0.75f, true);
        this.capacity = capacity;
    }

    /**
     * 返回 true表示删除最近最少使用的数据，就是链表删除头部节点的位置的数
     * LinkedHashMap中直接返回false
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > capacity;
    }

    public static void main(String[] args) {
        LRUCache lru = new LRUCache(5);
        lru.put("1", 1);
        lru.put("2", 2);
        lru.put("3", 3);
        lru.put("4", 4);
        lru.put("5", 5);

        lru.forEach((k, v) -> System.out.println("k="+ k +", v=" + v));
        System.out.println(lru.get("1"));
        System.out.println(lru.get("2"));
        System.out.println(lru.get("3"));
        System.out.println(lru.get("5"));
        lru.forEach((k, v) -> System.out.println("k="+ k +", v=" + v));
        lru.put("6", 6);
        System.out.println("-----------------");
        lru.forEach((k, v) -> System.out.println("k="+ k +", v=" + v));
    }
}
