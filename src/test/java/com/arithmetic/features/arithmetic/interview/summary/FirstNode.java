package com.arithmetic.features.arithmetic.interview.summary;


/**
 * @author alan.chen
 * @date 2020/7/23 6:39 PM
 */
public class FirstNode {


    public static void main(String[] args) {
        Node head = new Node();
        findFirstNode(head);
    }


    /**
     * 找到成环的第一个节点数据
     * @param head
     */
    private static void findFirstNode(Node head) {

    }


    static class Node {
        int val;
        Node next;
    }
}
