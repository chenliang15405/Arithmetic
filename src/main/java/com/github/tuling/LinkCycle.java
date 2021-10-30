package com.github.tuling;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author tangsong
 * @date 2021/5/2 18:29
 */
public class LinkCycle {

    public static class Node {
        private int val;
        private Node next;

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
    public void test() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node3;

        System.out.println(hasCycle(node1));

        System.out.println(hasCycle2(node1));
    }


    /**
     * 快慢指针
     *
     */
    private boolean hasCycle(Node head) {
        if(head == null) {
            return false;
        }
        // 快慢指针
        Node fast = head.next;
        Node slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow) {
                return true;
            }
        }
        return false;
    }


    /**
     * 集合
     */
    private boolean hasCycle2(Node head) {
        if(head == null) {
            return false;
        }
        Set<Node> set = new HashSet<>();
        Node cur = head;
        while (cur != null) {
            if(set.contains(cur)) {
                return true;
            }
            set.add(cur);
            cur = cur.next;
        }
        return false;
    }

}
