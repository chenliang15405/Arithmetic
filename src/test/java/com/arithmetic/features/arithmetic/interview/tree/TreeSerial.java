package com.arithmetic.features.arithmetic.interview.tree;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树的序列化和反序列化
 *
 * 前序序列化、反序列化
 *
 * 二叉树按照层级的序列化、反序列化
 *
 * @author alan.chen
 * @date 2020/6/9 9:55 PM
 */
public class TreeSerial {

    @Test
    public void test() {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        String res = treeSerial(head);
        System.out.println("序列化：" + res);


        Node node = treeSerialReconvert(res);
        System.out.println("索引反序列化");
        preOrder(node);

        Node node1 = treeSerialReconvertWithQueue(res);
        System.out.println("队列反序列化");
        preOrder(node1);

    }

    /**
     * 序列化
     * # 表示为null，!表示分隔
     * @param head
     * @return
     */
    public String treeSerial(Node head) {
        if(head == null) {
            return "#!";
        }
        String res = "";
        res = head.value + "!";

        res += treeSerial(head.left);
        res += treeSerial(head.right);
        return res;
    }

    /**
     * 使用队列反序列化
     * 将数据都加入队列中，按照前序的方式反序列化
     *
     * @param str
     * @return
     */
    public Node treeSerialReconvertWithQueue(String str) {
        String[] splits = str.split("!");

        Queue<String> queue = new LinkedList<>();

        // 将数据加入队列
        for (int i = 0; i < splits.length; i++) {
            queue.offer(splits[i]);
        }

        return serialConvert(queue);
    }

    private Node serialConvert(Queue<String> queue) {
        String str = queue.poll();
        if(str.equals("#")) {
            return null;
        }
        Node node = new Node(Integer.valueOf(str));

        node.left = serialConvert(queue);
        node.right = serialConvert(queue);

        return node;
    }


    /**
     * 前序反序列化，使用索引
     * @param str
     * @return
     */
    public Node treeSerialReconvert(String str) {
        String[] splits = str.split("!");
        return reconvert(splits);
    }

    private int index  = 0;
    private Node reconvert(String[] splits) {
        if(splits[index].equals("#")) {
            index++;
            return null;
        }
        Node node = new Node(Integer.valueOf(splits[index]));
        index++;
        node.left = reconvert(splits);
        node.right = reconvert(splits);

        return node;
    }

    private void preOrder(Node head) {
        Stack<Node> stack = new Stack<>();
        stack.add(head);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.print(node.value + "->");
            if(node.right != null) {
                stack.add(node.right);
            }
            if(node.left != null) {
                stack.add(node.left);
            }
        }
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
