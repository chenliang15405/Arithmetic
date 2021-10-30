package com.github.bstation.tree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangsong
 * @date 2021/5/30 17:22
 */
public class BSTtoDoubleLinkedList {

    /**
     * 双向链表节点结构和二叉树的节点结构是一样的，如果你把last指针认为是left指针，next指针认为是right指针的话。
     *  给定一个搜索二叉树的头结点head，请转化成一条有序的双向链表，并返回链表的头结点
     *
     * 二叉树的递归套路问题！
     *  问左子树要信息，向右子树要信息，
     *  组合左子树和右子树以及自己的信息作为当前树的信息返回
     *  子树和整颗树的结构是一样的，所以递归即可
     */


    @Test
    public void test() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        node4.left = node2;
        node4.right = node6;
        node2.left = node1;
        node2.right = node3;
        node6.left = node5;
        node6.right = node7;

        LinkedListNode linkedListNode = toDoubleLinkedList(node4);

        System.out.println(linkedListNode);

        Node head = toDoubleLinkedList2(node4);
        printLinkedList(head);
    }

    private void printLinkedList(Node head) {
        while (head != null) {
            System.out.print(head.value + " ");
            System.out.print("left=" + (head.left != null ? head.left.value : "null  "));
            System.out.print("right=" + (head.right != null ? head.right.value : "null  "));
            System.out.println();
            head = head.right;
        }
    }


    /**
     * 二叉树转换为双向链表
     *
     * 第一种方式，将二叉树中序遍历转换为数组，然后构建链表
     *
     * 时间复杂度 O(2N)
     *
     */
    public LinkedListNode toDoubleLinkedList(Node root) {
        // 中序遍历将节点的值放到数组中
        List<Integer> list = new ArrayList<>();
        process(root, list);
        // 遍历数组，构建链表，中序遍历肯定是有序的
        LinkedListNode head = new LinkedListNode(0);
        LinkedListNode cur = head;
        for (Integer value : list) {
            LinkedListNode temp = new LinkedListNode(value);
            cur.next = temp;
            temp.last = cur;
            cur = temp;
        }
        if(head.next != null) {
            head.next.last = null;
        }
        return head.next;
    }

    private void process(Node head, List<Integer> list) {
        if(head == null) {
            return;
        }
        process(head.left, list);
        list.add(head.value);
        process(head.right, list);
    }


    /**
     * 二叉树转换为链表第二种方式
     * 树形DP套路
     *
     * 1. 定义结构，向左树和右树要任何信息 -> 链表的开始节点和结束节点
     * 2. 每颗二叉树都返回相同的信息，并递归
     *
     *
     */
    public Node toDoubleLinkedList2(Node root) {
        return processToLinkedList(root).start;
    }

    private Info processToLinkedList(Node X) {
        if(X == null) {
            // 这里不好给默认值，那么就给null，使用的时候判断一下就行
            return new Info(null, null);
        }
        // 左树的信息, 返回的是子树构建的链表的开始节点和尾节点
        Info leftInfo = processToLinkedList(X.left);
        // 右树的信息
        Info rightInfo = processToLinkedList(X.right);
        // 构建当前子树的信息
        if(leftInfo.end != null) {
            // 将开始节点的next（right）指针指向当前节点
            leftInfo.end.right = X;
        }
        X.left = leftInfo.end;
        X.right = rightInfo.start;
        if(rightInfo.start != null) {
            // 将结尾节点的last(left)指针指向当前节点
            rightInfo.start.left = X;
        }

        // 构建当前节点返回，作为当前树的信息，如果子树，那么返回这个信息和整体树返回的信息结构是相同的
        return new Info(leftInfo.start != null ? leftInfo.start : X,
                        rightInfo.end != null ? rightInfo.end : X);
    }


    public static class Info {
        public Node start;
        public Node end;

        public Info(Node start, Node end) {
            this.start = start;
            this.end = end;
        }
    }





    public static class LinkedListNode {
        public int value;
        public LinkedListNode last;
        public LinkedListNode next;

        public LinkedListNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "LinkedListNode{" +
                    "value=" + value +
                    ", last=" + (last == null ? "null" : last.value) +
                    ", next=" + next +
                    '}';
        }
    }

}
