package com.github.bstation.linkedlist;

import com.github.tuling.MergeSortArray;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/6/5 10:48
 */
public class CopyListWithRandom {

    /**
     * 一种特殊的链表节点描述如下
     * class Node {
     *     int value;
     *     Node next;
     *     Node rand;
     *     Node(int val) {
     *         value = val;
     *     }
     * }
     *
     * rand指针指向单链表结构中的新增的指针，rand可能指向链表中的任意一个节点，也可能指向null。给定一个由Node
     * 节点类型组成的无环单链表的头结点head，请事先一个函数完成的链表复制，并返回复制的新链表的头节点
     *
     *  要求：
     *   时间复杂度O(N)，额外空间复杂度O(1)
     *
     *
     *   两种方式：
     *     1. 使用Map，额外空间复杂度O(N)
     *     2. 使用指针将复制的链表和原链表放在一个链表中，然后在进行分离
     */

    public static class Node {
        public int value;
        public Node next;
        public Node rand;

        public Node(int value) {
            this.value = value;
        }
    }

    @Test
    public void test() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.next = node2;
        node1.rand = node3;
        node2.next = node3;
        node2.rand = node1;
        node3.next = node4;
        node3.rand = null;
        node4.next = node5;
        node4.rand = node1;
        node5.rand = node4;

        Node node = copyWithArray(node1);
        printNode(node);

        System.out.println("==========================");

        Node nodeCopy2 = copyWithPoint(node1);
        printNode(nodeCopy2);

    }

    /**
     * 复制链表
     *   使用额外空间的方式
     *
     *  使用Map进行赋值， key-> 原链表节点， value -> 复制的链表节点
     *  遍历链表，拿到每个原链表的节点，并将所有的复制的链表根据原链表的节点连接起来
     *
     */
    public Node copyWithArray(Node head) {
        if(head == null) {
            return head;
        }
        Map<Node, Node> map = new HashMap<>();
        Node cur = head;
        // 遍历链表，复制每个节点放入到Map中
        while (cur != null) {
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }
        // 遍历链表，将复制的节点链接起来
        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }
        return map.get(head);
    }


    /**
     * 复制链表
     *   使用指针的方式，额外空间复杂度O(1)
     *
     */
    public Node copyWithPoint(Node head) {
        if(head == null) {
            return null;
        }
        // 遍历链表，并复制每个节点，将复制的节点链接到当前节点的next
        Node cur = head;
        Node next = null;
        Node copy = null;
        while (cur != null) {
            next = cur.next;
            copy = new Node(cur.value);
            cur.next = copy; // 当前节点的next指向copy的节点
            copy.next = next; // copy的节点的next指针指向原始链表的next节点
            cur = next; // 向下移动
        }
        // 链接每个复制节点的rand指针
        cur = head;
        while (cur != null) {
            // 记录原始链表的下一个节点
            next = cur.next.next;
            // 如果当前节点的rand节点没有指向null，那么就将copy的rand指针指向原始链表的节点的next（因为原始节点的next就是copy的节点）
            cur.next.rand = cur.rand == null ? null : cur.rand.next;
            cur = next;
        }

        // 分离原始链表和复制的链表
        cur = head;
        Node res = cur.next; // 记录复制链表的头节点
        while (cur != null) {
            next = cur.next.next;
            copy = cur.next;
            cur.next = next; // 将原始链表正确连接
            copy.next = next != null ? next.next : null; // 将复制的链表每个节点相连
            cur = next;
        }
        return res;
    }





    public static void printNode(Node head) {
        if(head == null) {
            return;
        }
        System.out.println("value=" + head.value + ",rand=" + (head.rand == null ? "null" : head.rand.value) + "=>");
        printNode(head.next);
    }
}
