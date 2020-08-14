package com.arithmetic.features.arithmetic.interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 将单向链表按照某值划分左边小、中间相等、右边大的形式
 *
 * 额外空间复杂度O(N)
 * 第一种：将链表中的所有节点加入数组中，将数组分为小于、等于、大于的区域，然后将所有节点重新链接
 *
 * 第二种：创建6个节点指针，第一次遍历将小于num的赋值到less指针，相等的赋值到eq指针，大于的赋值到more指针
 * 然后再遍历一次，将小于的挂载到less的next，相等的挂载到eq，大于的挂载到more，每个指针还有一个指针指向各自的结尾，
 * 然后将less的结尾链接eq的开头，将eq的极为连接到more的开头
 *
 * @author alan.chen
 * @date 2020/6/7 7:16 PM
 */
public class SmallerEqualsBigger {


    @Test
    public void test() {
        Node head = new Node(7);
        head.next = new Node(9);
        head.next.next = new Node(1);
        head.next.next.next = new Node(8);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(2);
        head.next.next.next.next.next.next = new Node(5);
        printLinkedList(head);

        head = listPartition(head, 5);

        System.out.println("对链表smaller equals bigger之后：");
        printLinkedList(head);
    }

    private Node listPartition(Node head, int key) {
        List<Node> list = new ArrayList<>();

        while (head != null) {
            list.add(head);
            head = head.next;
        }
        // 对list做partition
        Node[] nodes = partition(list, key);

        return nodes[0];
    }

    private Node[] partition(List<Node> list, int key) {
        int right = list.size() - 1;
        int left = 0;
        int less = left - 1;
        Node[] nodes = new Node[list.size()];
        nodes = list.toArray(nodes);

        while (left <= right) {
            if(nodes[left].value > key) {
                swap(nodes, left, right);
                right--;
            } else if(nodes[left].value < key) {
                swap(nodes, ++less, left);
                left++;
            } else {
                left++;
            }
        }
        return nodes;
    }

    private void swap(Node[] nodes, int left, int right) {
        int temp = nodes[left].value;
        nodes[left].value = nodes[right].value;
        nodes[right].value = temp;
    }


    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

}
