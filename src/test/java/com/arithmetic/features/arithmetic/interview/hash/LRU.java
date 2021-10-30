package com.arithmetic.features.arithmetic.interview.hash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现LRU算法
 *
 *  1. 实现LinkedHashMap
 *  2. 自定义实现Node
 *
 * @author tangsong
 * @date 2021/3/13 20:09
 */
public class LRU {

    /**
     * 定义Node对象保存key、value， Node节点用于保存节点的数据
     */
    public static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> next;
        public Node<K, V> last;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 双向链表，只用来维护Node节点的顺序，用于操作节点的顺序和淘汰Node节点
     */
    public static class NodeDoubleLinkedList<K, V> {
        public Node<K, V> head;
        public Node<K, V> tail;


        /**
         * 双向链表中添加新的节点
         * @param newNode 待添加节点
         */
        public void addNode(Node<K, V> newNode) {
            if(head == null) {
                this.head = newNode;
                this.tail = newNode;
            } else {
                this.tail.next = newNode;
                newNode.last = this.tail;
                this.tail = newNode;
            }
        }

        /**
         * 将节点移动到双向链表尾部
         * @param node 待移动节点
         */
        public void moveNodeToTail(Node<K, V> node) {
            if(this.tail == node) {
                return;
            }
            if(this.head == node) {
                this.head = node.next;
                this.head.last = null;
            } else {
                node.last.next = node.next;
                node.next.last = node.last;
            }
            node.last = this.tail;
            node.next = null;
            this.tail.next = node;
            this.tail = node;
        }


        /**
         * 移除头结点
         * @return 返回移除的头结点
         */
        public Node<K, V> removeHead() {
            if(this.head == null) {
                return null;
            }
            Node<K, V> temp = this.head;
            if(this.head == this.tail) {
                this.head = null;
                this.tail = null;
            } else {
                this.head = temp.next;
                this.head.last = null;
                temp.next = null;
            }
            return temp;
        }
    }



    public static class LRUCache<K, V> {
        public Map<K, Node<K, V>> keyNodeMap;
        public NodeDoubleLinkedList<K, V> linkedList;
        public int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            keyNodeMap = new HashMap<>();
            linkedList = new NodeDoubleLinkedList<>();
        }


        public void set(K key, V value) {
            // 如果key已存在，则重新赋值并将节点从双向链表中移动到最后节点
            if(keyNodeMap.containsKey(key)) {
                Node<K, V> node = keyNodeMap.get(key);
                node.value = value;
                linkedList.moveNodeToTail(node);
            } else {
                // 如果key不存在，则添加新的节点到双向链表头部，并判断当前链表的长度是否超过阈值，如果超过则移除双向链表的头结点
                Node<K, V> newNode = new Node<>(key, value);
                linkedList.addNode(newNode);
                keyNodeMap.put(key, newNode);
                if(keyNodeMap.size() > capacity) {
                    // 移除双向链表头结点
                    Node<K, V> headNode = linkedList.removeHead();
                    // 在map中移除
                    if(headNode != null) {
                        keyNodeMap.remove(headNode.key);
                    }
                }
            }
        }

        public V get(K key) {
            if(!keyNodeMap.containsKey(key)) {
                return null;
            }
            // 获取到节点的vlaue，并将该节点移动到双向链表的尾部
            Node<K, V> node = keyNodeMap.get(key);
            linkedList.moveNodeToTail(node);
            return node.value;
        }

    }


    @Test
    public void test() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(3);
        lruCache.set("A", 1);
        lruCache.set("B", 2);
        lruCache.set("C", 3);

        System.out.println(lruCache.get("A"));
        System.out.println(lruCache.get("B"));
        System.out.println(lruCache.get("C"));

        lruCache.set("D", 5);

        System.out.println("===========================");
        System.out.println(lruCache.get("A"));
        System.out.println(lruCache.get("B"));
        System.out.println(lruCache.get("C"));
        System.out.println(lruCache.get("D"));

    }

}
