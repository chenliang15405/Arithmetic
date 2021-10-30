package com.github.bstation.linkedlist;

import org.junit.Test;

import java.util.Stack;

/**
 * @author tangsong
 * @date 2021/6/3 22:23
 */
public class IsPalindRomeList {

    /**
     * 判断一个链表是否是回文链表
     *
     * 1. 使用栈保存链表，并和原链表比对
     * 2. 使用栈、快慢指针保存n/2的链表节点，判断是否回文
     * 3. 使用快慢指针，将链表的另一半进行逆序并进行比对
     *
     */

    @Test
    public void test() {
        Node head = new Node(1);
        Node node1 = new Node(2);
        Node node2 = new Node(3);
        Node node5 = new Node(3);
        Node node3 = new Node(2);
        Node node4 = new Node(1);
        head.next = node1;
        node1.next = node2;
        node5.next = node3;
        node3.next = node4;
        node2.next = node5;

        System.out.println(way1(head));
        System.out.println(way2(head));
        System.out.println(way3(head));

    }


    /**
     * 使用栈的方式
     *  空间复杂度：O(N)
     *  时间复杂度：O(N)
     *
     */
    public boolean way1(Node head) {
        Stack<Node> stack = new Stack<>();
        // 将链表节点都保存到栈中
        Node cur1 = head;
        while (cur1 != null) {
            stack.push(cur1);
            cur1 = cur1.next;
        }
        cur1 = head;
        while (!stack.isEmpty()) {
            if(cur1.value != stack.pop().value) {
                return false;
            }
            cur1 = cur1.next;
        }
        return true;
    }


    /**
     * 快慢指针
     * 使用n/2的空间
     *   空间复杂度：O(n/2)
     *   时间复杂度：O(N)
     */
    public boolean way2(Node head) {
        Stack<Node> stack = new Stack<>();

        Node slow = head;
        Node fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        while (slow != null) {
            stack.push(slow);
            slow = slow.next;
        }
        slow = head;
        while (!stack.isEmpty()) {
            if(slow.value != stack.pop().value) {
                return false;
            }
            slow = slow.next;
        }
        return true;
    }


    /**
     * 快慢指针，不适用额外空间
     *  将链表的一般进行逆序，并进行对比，判断完成将链表还原
     *
     *  空间复杂度：O(1)
     *  时间复杂度：O(N)
     */
    public boolean way3(Node head) {
        Node slow = head;
        Node fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 将slow节点的后一半进行反转
        Node cur = slow;
        Node next = null;
        Node pre = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        // 判断是否回文链表
        boolean res = true;
        Node temp = head;
        cur = pre;
        while (temp != null) {
            if(temp.value != cur.value) {
                res = false;
                break;
            }
            temp = temp.next;
            cur = cur.next;
        }
        // 将链表还原,再次反转
        cur = pre;
        pre = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return res;
    }

}
