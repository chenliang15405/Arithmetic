package com.arithmetic.features.datastructure.hashtable;

import org.junit.Test;

/**
 * @author alan.chen
 * @date 2020/6/5 2:25 PM
 */
public class HashMapDemo {

    @Test
    public void test() {
        String str = "123";

        HashMap<String, Integer> map = new HashMap<>(8);
        map.put("一",1 );
        map.put("一",2 );

        System.out.println(map.get("一"));
        System.out.println(map.get("一"));
        System.out.println(map.get("三"));

        map.list("一");
    }

    @Test
    public void test1() throws Exception {
        String str = "123";

        HashMapDesign<String, Integer> map = new HashMapDesign<>(2);
        map.put("一",1 );
        map.put("一",2 );
        map.put("一",2 );
        map.put("一",2 );

    }

}

class HashMap<K, V> {

    private Entry<K, V>[] entry;

    private static final int DEFAULT_CAPACITY = 8;

    private int maxSize; // 用来记录数组最大值，计算hash

    public HashMap(int maxSize) {
        entry = new Entry[maxSize];
        this.maxSize = maxSize;
        // 初始化数组中的所有entry对象
        for (int i = 0; i < entry.length; i++) {
            entry[i] = new Entry<K, V>();
        }
    }

    public HashMap() {
        entry = new Entry[DEFAULT_CAPACITY];
        this.maxSize = maxSize;
        for (int i = 0; i < entry.length; i++) {
            entry[i] = new Entry<K, V>();
        }
    }

    public void put(K key, V value) {
        // hash
        int hash = hash(key);
        entry[hash].add(new Node<K, V>(key, value));
    }

    public V get(K key) {
        int hash = hash(key);
        return entry[hash].get(key);
    }

    public void list(K key) {
        int hash = hash(key);
        entry[hash].list();
    }

    private int hash(K key) {
        return key == null ? 0 : key.hashCode() % maxSize;
    }


    class Entry<K, V> {
        private Node<K, V> head;


        public void add(Node<K, V> kvNode) {
            if(head == null) {
                head = kvNode;
            } else {
                Node<K, V> cur = head;
                while (cur.getNext() != null) {
                    if(cur.getKey().equals(kvNode.getKey())) {
                        cur.setValue(kvNode.getValue());
                        return;
                    }
                    cur = cur.getNext();
                }
                if(cur.getKey().equals(kvNode.getKey())) {
                    cur.setValue(kvNode.getValue());
                    return;
                }
                cur.setNext(kvNode);
            }
        }

        public V get(K key) {
            Node<K, V> cur = head;
            while (cur != null) {
                if(cur.getKey().equals(key)) {
                    return cur.getValue();
                }
                cur = cur.getNext();
            }
            return null;
        }

        public void list() {
            Node<K, V> cur = head;
            System.out.println(cur);
        }

    }

    class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", next=" + next +
                    '}';
        }

    }

}

