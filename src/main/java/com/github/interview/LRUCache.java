package com.github.interview;

import com.github.左神算法.哈希函数.Hash;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/9/19 0:26
 */
public class LRUCache {

    public static class Node<K, V> {
        public K key;
        public V val;
        public Node<K, V> pre;
        public Node<K, V> next;

        public Node() {

        }
        public Node(K k, V v) {
            this.key = k;
            this.val = v;
        }
    }

    public static class DoubleLinkedList<K, V> {
        public Node<K, V> head;
        public Node<K, V> tail;

        public DoubleLinkedList() {
        }

        // 添加节点到链表尾部
        public void addNode(Node<K, V> node) {
            if(head == null && tail == null) {
                head = node;
                tail = node;
            } else {
                node.pre = tail;
                tail.next = node;
                tail = node;
            }
        }

        // 删除头结点
        public Node<K, V> removeHead() {
            if(head == null) {
                throw new RuntimeException("linked list is null");
            }
            Node<K, V> temp = head;
            if(head == tail) {
                head = null;
                tail = null;
            } else {
                head.next.pre = null;
                head = head.next;
                temp.next = null;
            }
            return temp;
        }

        // 移动节点到链表尾部
        public void moveNodeToTail(Node<K, V> node) {
            if(head == null && tail == null) {
                throw new RuntimeException("linked list is null");
            }
            if(node == head) {
                removeHead();
                node.pre = tail;
                tail.next = node;
                tail = node;
                return;
            }
            if(node == tail) {
                return;
            }
            node.pre.next = node.next;
            node.next.pre = node.pre;

            node.pre = tail;
            tail.next = node;
            node.next = null;
            tail = node;
        }
    }

    public static class LRU<K, V> {
        public DoubleLinkedList<K, V> linkedList;
        public Map<K, Node<K, V>> map;
        public int capacity;

        public LRU(int capacity) {
            this.capacity = capacity;
            map = new HashMap<>();
            linkedList = new DoubleLinkedList<>();
        }

        public void put(K k, V v) {
            if(map.containsKey(k)) {
                Node<K, V> node = map.get(k);
                node.val = v;
                linkedList.moveNodeToTail(node);
            } else {
                Node<K, V> node = new Node<>(k, v);
                map.put(k, node);
                linkedList.addNode(node);
                // 判断是否需要lru
                if(map.size() > capacity) {
                    Node<K, V> removeHead = linkedList.removeHead();
                    if(removeHead != null) {
                        map.remove(removeHead.key);
                    }
                }
            }
        }

        public V get(K k) {
            if(!map.containsKey(k)) {
                return null;
            }
            Node<K, V> node = map.get(k);
            linkedList.moveNodeToTail(node);
            return node.val;
        }
    }

    @Test
    public void test() {
        LRU<Integer, Integer> lru = new LRU<>(3);
        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);
        lru.get(1);
        lru.put(4, 4);

        System.out.println(lru.get(1));
        System.out.println(lru.get(2));
        System.out.println(lru.get(3));
        System.out.println(lru.get(4));
    }

}
