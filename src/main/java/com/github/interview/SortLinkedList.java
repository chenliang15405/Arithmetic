package com.github.interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangsong
 * @date 2021/10/12 19:03
 */
public class SortLinkedList {

    public static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    /**
     * 单链表奇数递增偶数递减，重排后使之升序
     *
     * 例如：
     *   1-> 6 -> 3 -> 4 -> 5 -> 2
     *   排序之后为：
     *   1 -> 2 -> 3 -> 4 -> 5 -> 6
     */
    @Test
    public void test() {
        Node head = new Node(1);
        Node node1 = new Node(6);
        Node node2 = new Node(3);
        Node node3 = new Node(4);
        Node node4 = new Node(5);
        Node node5 = new Node(2);
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        Node node = sortLinkedList3(head);

        while(node != null) {
            System.out.print(node.val + "->");
            node = node.next;
        }

    }


    /**
     * 将链表拆分
     * 对偶数链表进行反转
     * 合并两个有序链表
     *
     * O(n)
     */
    public Node sortLinkedList3(Node head) {
        if(head == null || head.next == null) return null;
        // 将链表拆分
        Node[] list = splitLinkedList(head);
        Node l1 = list[0];
        Node l2 = list[1];
        // 反转偶数位的链表
        l2 = reverse(l2);
        // 合并两个有序链表
        Node newHead = mergeLinkedList(l1, l2);

        return newHead;
    }

    private Node mergeLinkedList(Node l1, Node l2) {
        Node newHead = new Node(0);
        Node cur = newHead;

        while (l1 != null && l2 != null) {
            if(l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        if(l1 != null) {
            cur.next = l1;
        }
        if(l2 != null) {
            cur.next = l2;
        }

        return newHead.next;
    }

    private Node reverse(Node head) {
        Node next = null;
        Node pre = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        return pre;
    }

    private Node[] splitLinkedList(Node head) {
        Node l1 = head;
        Node l2 = head.next; // 链表2的头结点

        while (l1 != null) {
            Node next = l1.next.next;
            Node evenNode = l1.next;
            l1.next = next;
            evenNode.next = next == null ? null : next.next;
            l1 = next;
        }

        // 返回两个链表的头节点
        return new Node[]{head, l2};
    }


    /**
     * O（n）时间复杂度，不过可能不会通过所有用例
     *
     */
    public Node sortLinkedList2(Node head) {
        if(head == null) return head;

        Node cur = head;
        List<Node> list = new ArrayList<>();

        while(cur != null) {
            list.add(cur);
            cur = cur.next;
        }
        int n = list.size();
        int i = 0;
        int j = n - 1;

        while (i < n && j >= 0) {
            list.get(i).next = list.get(j);
            if(i + 2 < n) {
                list.get(j).next = list.get(i + 2);
            }
            i += 2;
            j -= 2;
        }
        if(j < 0 && i >= n) {
            list.get(j + 2).next = null;
        } else {
            list.get(i).next = null;
        }

        return list.get(0);
    }

    /**
     * 链表 + 排序
     *
     */
    public Node sortLinkedList(Node head) {
        if(head == null) return head;

        Node cur = head;
        List<Node> list = new ArrayList<>();

        while(cur != null) {
            list.add(cur);
            cur = cur.next;
        }

        list.sort((a, b) -> a.val - b.val);
        for (int i = 0; i < list.size() - 1; i++) {
            list.get(i).next = list.get(i + 1);
        }
        list.get(list.size() - 1).next = null;

        return list.get(0);
    }

}
