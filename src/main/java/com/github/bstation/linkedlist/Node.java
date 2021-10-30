package com.github.bstation.linkedlist;

/**
 * @author tangsong
 * @date 2021/6/5 16:21
 */
public class Node {

    public int value;
    public Node next;

    public Node(int value) {
        this.value = value;
    }

    public static void printNode(Node head) {
        if(head == null) {
            System.out.print(head);
            return;
        }
        System.out.print(head.value + "=>");
        printNode(head.next);
    }

}
