package com.github.bstation.linkedlist;

/**
 * @author tangsong
 * @date 2021/6/3 22:05
 */
public class LinkedListMid {

    /**
     * 链表问题 —— 快慢指针
     *  1. 输入链表的头节点，奇数长度返回中点，偶数长度返回上中点
     *  2. 输入链表的头节点，奇数长度返回中点，偶数长度返回下中点
     *  3. 输入链表的头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
     *  4. 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
     *
     *
     *  两种方式可以解：
     *    1. 将链表转换容器求中点
     *    2. 使用快慢指针-> 每种条件的边界情况不一样
     *
     */


    /**
     * 输入链表的头节点，奇数长度返回中点，偶数长度返回上中点
     */
    public Node getNode(Node head) {
        Node slow = head;
        Node fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }





}
