package com.github.tuling;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/2 13:57
 */
public class ReverseLinkNode {

    public static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }

    @Test
    public void test1() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

//        Node node = doublePoint(node1);
        Node node = recursion(node1);

        System.out.println(node);
    }

    /**
     * 双指针法
     */
    public Node doublePoint(Node head) {
        Node next = null;
        Node pre = null;
        Node cur = head;

        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        // 当cur等于null时,pre指向最后一个节点
        return pre;
    }


    /**
     * 递归反转
     *
     */
    public Node recursion(Node head) {
        // base case
        if(head == null || head.next == null) {
            return head;
        }
        // 因为链表是不可逆的，所以当head.next等于Null的时候，就表示为最后一个节点了，因为需要反转，操作的是倒数第二个节点，所以这里传的是head.next，
        // 当到达最后一个节点，那么此时可以拿到倒数第二个节点
        // 返回的是最后一个节点，只是用作返回值，构建为新的链表头部时使用
        Node newHead = recursion(head.next);
        head.next.next = head; // 反转指针
        head.next = null; // 避免成环
        return newHead;
    }


}
