package com.arithmetic.features.arithmetic.interview.tree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author tangsong
 * @date 2021/3/27 22:39
 */
public class TreeCheck {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    @Test
    public void test1() {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(8);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.left.left.left = new Node(1);
        head.right.left = new Node(7);
        head.right.left.left = new Node(6);
        head.right.right = new Node(10);
        head.right.right.left = new Node(9);
        head.right.right.right = new Node(11);

        Node head1 = new Node(5);
        head1.left = new Node(1);
        head1.right = new Node(4);
        head1.right.left = new Node(3);
        head1.right.right = new Node(6);

        boolean bst = isBSTWithInfixOrder(head);
        System.out.println(bst);

        boolean bst1 = isBSTWithInfixOrder(head1);
        System.out.println(bst1);

        boolean bstWithMorris = isBSTWithMorris(head);
        System.out.println(bstWithMorris);
        boolean bstWithMorris1 = isBSTWithMorris(head1);
        System.out.println(bstWithMorris1);
    }

    @Test
    public void isCBT() {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(8);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.left.left.left = new Node(1);
        head.right.left = new Node(7);
        //head.right.left.left = new Node(6);
        head.right.right = new Node(10);
        //head.right.right.left = new Node(9);
        //head.right.right.right = new Node(11);

        boolean cbt = isCBT(head);
        System.out.println("是否是完全二叉树：" + cbt);
    }


    /**
     * 判断是否为排序二叉树
     *   中序遍历方式
     *
     */
    public boolean isBSTWithInfixOrder(Node head) {
        if(head == null) {
            return false;
        }
        List<Integer> list = new ArrayList<>();
        infixOrder(head, list);
        // 判断list是否递增
        for (int i = 1; i < list.size(); i++) {
            if(list.get(i) < list.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    private void infixOrder(Node head, List<Integer> list) {
        if(head == null) {
            return;
        }
        infixOrder(head.left, list);
        list.add(head.value);
        infixOrder(head.right, list);
    }


    /**
     * 判断是否为排序二叉树
     *   morris遍历方式
     */
    public boolean isBSTWithMorris(Node head) {
        if(head == null) {
            return false;
        }
        Node cur = head;
        Node mostRight = null;
        int pre = Integer.MIN_VALUE;
        while (cur != null) {
            mostRight = cur.left;
            if(mostRight != null) {
                // 找到左子树的最右节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null) {
                    // 将mostRight指向当前节点
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            // 获取到当前节点的值
            if(cur.value < pre) { // 中序遍历的方式，从小到大
                return false;
            }
            pre = cur.value;

            cur = cur.right;
        }
        return true;
    }


    /**
     * 判断是否完全二叉树
     */
    public boolean isCBT(Node head) {
        if(head == null) {
            return false;
        }
        boolean leaf = false;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if(node != null) {

                if(node.left == null && node.right != null) {
                    return false;
                }
                if(leaf && (node.left != null || node.right != null)) {
                    return false;
                }
                if(node.left == null && node.right == null) {
                    leaf = true;
                }
                if(node.left != null) {
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return true;
    }


}
