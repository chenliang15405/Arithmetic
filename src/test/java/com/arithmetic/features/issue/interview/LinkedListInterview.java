package com.arithmetic.features.issue.interview;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author alan.chen
 * @date 2020/6/17 3:31 PM
 */
public class LinkedListInterview {

    /**
     * 给定一个链表的head跟数字k，反转从head开始的交替间隔的大小为k的子列表
     */
    @Test
    public void q1() {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        int k = 2;

        reverseK(head, k);

    }



    private Node reverseK(Node head, int k) {
        if (k <= 1 || head == null)
            return head;

        Node current = head, previous = null;
        while (true) {
            Node lastNodeOfPreviousPart = previous;
            // 反转结束之后，current就是子列表的最后一个节点
            Node lastNodeOfSubList = current;
            Node next = null; // 用来临时存储下一个节点
            //反转k个节点
            for (int i = 0; current != null && i < k; i++) {
                next = current.next;
                current.next = previous;
                previous = current;
                current = next;
            }

            //跟前面的部分链接
            if (lastNodeOfPreviousPart != null)
                lastNodeOfPreviousPart.next = previous; // previous现在是子列表的第一个节点
            else // 这意味着我们在处理第一个子列表
                head = previous;

            //跟下一部分链接
            lastNodeOfSubList.next = current;

            if (current == null) //到达最后，结束循环
                break;
            // 为下一个子列表做准备
            previous = lastNodeOfSubList;
        }

        return head;
    }


    public static class Node {
        public int value;
        public Node next;
        public Node rand;

        public Node(int data) {
            this.value = data;
        }
    }


}
