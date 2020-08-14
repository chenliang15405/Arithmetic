package com.arithmetic.features.arithmetic.interview;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *  将一个每个节点还有随机指针的单向链表复制一个新的链表，并返回复制的头节点
 *
 *  思路：
 *   第一种方式：使用hashMap 额外空间复杂度：O(N)，将原链表作为map的key，复制的链表作为value,遍历原链表时从map中每次获取
 *              复制的链表来达到将复制的链表链接
 *
 *   第二种方式：将复制的节点挂载到当前节点的下一个节点，获取到当前节点的下一个节点，将当前节点的下一个节点（复制节点）
 *   指向当前节点的next节点的下一个几点就是复制的next节点
 *   1->1^->2->2^->
 *
 * @author alan.chen
 * @date 2020/6/7 7:41 PM
 */
public class CopyLinkedListWithRandom {

    public static class Node {
        public int value;
        public Node next;
        public Node rand;

        public Node(int data) {
            this.value = data;
        }
    }


    @Test
    public void test() {
        Node head = null;
        Node res1 = null;
        Node res2 = null;
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        head.rand = head.next.next.next.next.next; // 1 -> 6
        head.next.rand = head.next.next.next.next.next; // 2 -> 6
        head.next.next.rand = head.next.next.next.next; // 3 -> 5
        head.next.next.next.rand = head.next.next; // 4 -> 3
        head.next.next.next.next.rand = null; // 5 -> null
        head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4

        printRandLinkedList(head);

        Node headCopy = copyLinkedListWithRandom(head);

        System.out.println("复制的链表");
        System.out.println("是否是原链表" + (headCopy == head));
        printRandLinkedList(headCopy);
    }

    /**
     * 使用hashMap存储原链表和复制链表，通过遍历原链表来达到将复制的链表重新链接
     * @param head
     * @return
     */
    private Node copyLinkedListWithRandom(Node head) {
        if(head == null) {
            return null;
        }
        Map<Node, Node> map = new HashMap<>();

        Node cur = head;
        // 将所有节点和复制节点保存到map中
        while (cur != null) {
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }
        cur = head;
        // 遍历原始链表，将复制节点的next和rand节点链接起来
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }
        return map.get(head);
    }


    public static void printRandLinkedList(Node head) {
        Node cur = head;
        System.out.print("order: ");
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
        cur = head;
        System.out.print("rand:  ");
        while (cur != null) {
            System.out.print(cur.rand == null ? "- " : cur.rand.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

}
