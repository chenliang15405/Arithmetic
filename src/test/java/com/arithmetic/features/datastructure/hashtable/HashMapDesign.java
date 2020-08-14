package com.arithmetic.features.datastructure.hashtable;

import org.junit.Test;

/**
 * @author alan.chen
 * @date 2020/7/23 10:51 AM
 */
public class HashMapDesign<K, V> {


    private Entry<K, V>[] entries;

    private int size;

    private int index;

    public HashMapDesign(int capacity) {
        this.size = capacity;
        entries = new Entry[capacity];
        //for (int i = 0; i < capacity; i++) {
        //    entries[i] = new Entry<>();
        //}
    }



    public void put(K key, V val) throws Exception {
        if(index > size) {
            throw new Exception("the map is full");
        }
        int hash = indexOf(key);
        if(entries[hash] == null) {
            entries[hash] = new Entry(key, val);
            index++;
        } else {
            Entry<K, V> entry = entries[hash];
            while(entry.next != null) {
                if(entry.key.equals(key)) {
                    entry.val = val;
                    return;
                }
                entry = entry.next;
            }
            // 到达最后，还需要判断一次最后的节点是否equals
            if(entry.key.equals(key)) {
                entry.val = val;
            } else {
                // 新加链表
                entry.next = new Entry(key, val);
                index++;
            }
        }
    }


    public int indexOf(K key) {
        int hash = key.hashCode();
        return hash % size;
    }

    static class Entry<K, V> {
        K key;
        V val;
        Entry next;

        public Entry() {
        }

        public Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

}
