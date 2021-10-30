package com.arithmetic.features.arithmetic.interview.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/3/21 18:38
 */
public class LRUDemo {

    /**
     * 定义Node对象
     */
    public static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> pre;
        public Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 双向链表
     */
    public static class DoubleNodeLinkedList<K, V> {
        public Node<K, V> head;
        public Node<K, V> tail;


        public void moveNodeToTail(Node<K, V> node) {
            if(node == tail) {
                return;
            }
            if(node == head) {
                head = node.next;
                head.pre = null;
            } else {
                node.pre.next = node.next;
                node.next.pre = node.pre;
            }
            node.next = null;
            tail.next = node;
            node.pre = tail;
            tail = node;
        }

        public void addNode(Node<K, V> newNode) {
            if(head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                newNode.pre = tail;
                tail = newNode;
            }
        }

        public Node<K, V> removeHead() {
            Node<K, V> node = head;
            if(head == tail) {
                head = null;
                tail = null;
            } else {
                Node<K, V> temp = head.next;
                head.next = null;
                head = temp;
                head.pre = null;
            }
            return node;
        }
    }

    public static class LRUCache<K, V> {
        public Integer capacity;
        public DoubleNodeLinkedList<K, V> linkedList;
        public Map<K, Node<K, V>> keyNodeMap;

        public LRUCache(Integer capacity) {
            this.capacity = capacity;
            linkedList = new DoubleNodeLinkedList<>();
            keyNodeMap = new HashMap<>(capacity);
        }

        /**
         * 保存
         */
        public void set(K key, V value) {
            if(keyNodeMap.containsKey(key)) {
                Node<K, V> node = keyNodeMap.get(key);
                node.value = value;
                linkedList.moveNodeToTail(node);
            } else {
                if(keyNodeMap.size() > capacity) {
                    Node<K, V> headNode = linkedList.removeHead();
                    if(headNode != null) {
                        keyNodeMap.remove(headNode.key);
                    }
                }
                Node<K, V> newNode = new Node<>(key, value);
                linkedList.addNode(newNode);
                keyNodeMap.put(key, newNode);
            }
        }

        /**
         * 获取
         */
        public V get(K key) {
            if(!keyNodeMap.containsKey(key)) {
                return null;
            }
            Node<K, V> node = keyNodeMap.get(key);
            linkedList.moveNodeToTail(node);
            return node.value;
        }

    }



}
