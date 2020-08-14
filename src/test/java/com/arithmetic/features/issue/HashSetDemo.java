package com.arithmetic.features.issue;

import org.junit.Test;


/**
 * @author alan.chen
 * @date 2020/6/11 5:27 PM
 */

public class HashSetDemo {

    @Test
    public void test() {
        MyHashSet hashSet = new MyHashSet(5);

        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(3);
        hashSet.add(2);
        hashSet.add(3);
        hashSet.add(5);

        System.out.println(hashSet.contains(1));
        System.out.println(hashSet.contains(2));
        System.out.println(hashSet.contains(3));
        System.out.println(hashSet.contains(4));

        hashSet.remove(2);

        System.out.println(hashSet.contains(1));
        System.out.println(hashSet.contains(2));
        System.out.println(hashSet.contains(3));

    }
}

class MyHashSet {

    private Entry[] entries;
    private int size;
    private int index;

    public MyHashSet(int size) {
        this.size = size;
        entries = new Entry[size];

    }

    class Entry {
        private int hash;
        private int key;
        private Entry next;

        public Entry(int hash, int key) {
            this.hash = hash;
            this.key = key;
        }

        public int getHash() {
            return hash;
        }

        public void setHash(int hash) {
            this.hash = hash;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public Entry getNext() {
            return next;
        }

        public void setNext(Entry next) {
            this.next = next;
        }
    }

    public int hash(int key) {
        return key % size;
    }


    /** Initialize your data structure here. */
    public MyHashSet() {
        this.size = 16;
        entries = new Entry[16];
    }

    public boolean checkSize() {
        return index >= size;
    }

    public void add(int key) {
        if(checkSize()) {
            throw new RuntimeException("hashset is full");
        }
        int hash = hash(key);
        Entry e = entries[hash];
        if(e == null) {
            entries[hash] = new Entry(hash, key);
            index++;
            return;
        }
        Entry cur = e;
        while (cur != null) {
            if(cur.getKey() == key) {
                entries[hash] = new Entry(hash, key);
                return;
            }
            cur = cur.next;
        }
        cur = e;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.setNext(new Entry(hash, key));
    }


    public void remove(int key) {
        if(index == 0) {
            throw new RuntimeException("hashset is empty");
        }
        int hash = hash(key);
        Entry e = entries[hash];
        if(e == null) {
            throw new RuntimeException("null entry");
        }
        while (e != null) {
            if(e.getKey() == key) {
                if(e.getNext() != null) {
                    e.setKey(e.next.key);
                    e.setNext(e.next.next);
                } else {
                    entries[hash] = null;
                }
                index--;
            }
            e = e.next;
        }
    }

    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        int hash = hash(key);
        Entry e = entries[hash];
        while (e != null) {
            if(e.getKey() == key) {
                return true;
            }
            e = e.next;
        }
        return false;
    }
}

