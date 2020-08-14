package com.arithmetic.features.arithmetic.interview;


import org.junit.Test;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 反转单向链表和双向链表
 *
 * @author alan.chen
 * @date 2020/6/7 5:54 PM
 */
public class ReverseLinkedList {


    @Test
    public void test() {
        DoubleNode head2 = new DoubleNode(1);
        head2.next = new DoubleNode(2);
        head2.next.last = head2;
        head2.next.next = new DoubleNode(3);
        head2.next.next.last = head2.next;
        head2.next.next.next = new DoubleNode(4);
        head2.next.next.next.last = head2.next.next;
        printDoubleLinkedList(head2);


        printDoubleLinkedList(reverseList(head2));
    }


    @Test
    public void reverseNode() {
        Node head = null;
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        head = reverseNode(head);

        while (head != null) {
            System.out.print(head.value + "->");
            head = head.next;
        }
    }

    @Test
    public void swapPairs() {
        Node head = null;
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        head = swapPairs(head);

        while (head != null) {
            System.out.print(head.value + "->");
            head = head.next;
        }
    }

    public Node swapPairs(Node head) {
        if(head.next == null) {
            return head;
        }
        Node pre = new Node(0);
        pre.next = head;

        Node temp = pre;

        while(temp.next != null && temp.next.next != null) {
            Node start = temp.next;
            Node end = temp.next.next;

            start.next = end.next;
            end.next = start;
            // 因为是新链表，需要将当前的下一个节点改为当前已经换过顺序的链表节点
            temp.next = end;

            temp = start;
        }
        return pre.next;
    }


    @Test
    public void nodeKth() {
        Node head = null;
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        Node node = NthNodeFromEnd(head, 1);
        System.out.println(node.value);
    }

    /**
     * 单链表倒数第K个节点
     *
     * 使用快、慢指针
     *
     * @param head
     */
    public Node NthNodeFromEnd(Node head, int n) {
        Node cur = head;
        Node fast = head;

        for (int i = 1; i < n; i++) {
            fast = fast.next;
        }
        while(fast.next != null) {
            cur = cur.next;
            fast = fast.next;
        }
        return cur;
    }

    @Test
    public void mergeNode() {
        Node head = null;
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);

        Node head2 = new Node(4);
        head2.next = new Node(5);
        head2.next.next = new Node(6);

        Node node = mergeSortNode(head, head2);

        while (node != null) {
            System.out.print(node.value + "->");
            node = node.next;
        }
    }

    /**
     * 合并两个有序链表
     * @param node1
     * @param node2
     */
    public Node mergeSortNode(Node node1, Node node2) {
        Node newNode = new Node(0);
        Node cur = newNode;

        while(node1 != null && node2 != null) {
            if(node1.value <= node2.value) {
                cur.next = new Node(node1.value);
                node1 = node1.next;
                cur = cur.next;
            } else {
                cur.next = new Node(node2.value);
                node2 = node2.next;
                cur = cur.next;
            }
        }

        if(node1 != null) {
            cur.next = node1;
        }
        if(node2 != null) {
            cur.next = node2;
        }

        return newNode.next;
    }

    public Node reverseNode(Node head) {
        Node cur = head;
        Node pre = null;
        Node next = null;

        while(cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 反转双向链表
     * @param head
     * @return
     */
    private DoubleNode reverseList(DoubleNode head) {
        DoubleNode cur = head;
        DoubleNode pre = null;
        DoubleNode next = null;

        while (cur != null) {
            // 保存下一个指针
            next = cur.next;
            // 设置next指针
            cur.next = pre;
            // 设置pre指针
            cur.last = next;
            // 将next设置为pre
            pre = cur;
            // 指向下一个页面
            cur = next;
        }
        return pre;
    }


    public static void printDoubleLinkedList(DoubleNode head) {
        System.out.print("Double Linked List: ");
        DoubleNode end = null;
        while (head != null) {
            System.out.print(head.value + " ");
            end = head;
            head = head.next;
        }
        System.out.print("| ");
        while (end != null) {
            System.out.print(end.value + " ");
            end = end.last;
        }
        System.out.println();
    }


    /**
     * 对一个链表，将 m 到 n之间的之间反转  0< m < n < 链表的长度
     */
    @Test
    public void test5() {
        Node head = null;
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);

        Node node = reverseBetween(head, 2, 4);

    }

    public Node reverseBetween(Node head, int m, int n) {
        int count = 1;
        Node cur = head;
        Node pre = null;
        Node next = null;
        Node origin = head;
        Node first = null;

        while(cur != null) {
            if(count >= m && count <= n) {
                first = cur;
                for(int i = 0; i <= n - m; i++) {
                    count++;
                    next = cur.next;
                    cur.next = pre;
                    pre = cur;
                    cur = next;
                }
                // 将第一个节点链接到cur，将第一个节点的前一个节点链接到pre
                first.next = cur;
                if(m != 1) {
                    origin.next = pre;
                }
                break;
            }
            count++;
            origin = cur;
            cur = cur.next;
        }
        if(m == 1) {
            return pre;
        }
        return head;
    }

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }

    public static class DoubleNode {
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data) {
            this.value = data;
        }
    }
}
