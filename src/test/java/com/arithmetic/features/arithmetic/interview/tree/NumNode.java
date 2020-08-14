package com.arithmetic.features.arithmetic.interview.tree;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 已知一棵完全二叉树，求其节点个数
 *
 * @author alan.chen
 * @date 2020/6/9 11:05 PM
 */
public class NumNode {


    @Test
    public void test() {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        int count = 0;
        count = countNode(head, count);

        System.out.println("节点个数: " + count);

    }

    public int countNode(Node head, int count) {
        Queue<Node> queue = new LinkedList<>();

        queue.offer(head);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                count++;
                if(node.left != null) {
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.left);
                }
            }
        }
        return count;
    }



    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

}
