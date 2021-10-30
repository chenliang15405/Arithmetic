package com.github.bstation.tree;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/9/20 0:39
 */
public class CompleteTreeLastNode {

    /**
     * 给定一棵完全二叉搜索树，返回最后一个最右边的节点
     *
     */

    @Test
    public void test() {
        Node node1 = new Node(5);
        Node node2 = new Node(3);
        Node node3 = new Node(7);
        Node node4 = new Node(2);
        Node node5 = new Node(4);
        Node node6 = new Node(6);
        Node node7 = new Node(8);
        Node node8 = new Node(9);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        node4.left = node8;

        System.out.println(getLastNode(node1).val);

    }


    public Node getLastNode(Node root) {

        return process(root);
    }

    private Node process(Node root) {
        if(root == null || root.left == null) {
            return root;
        }
        // 计算左子树的高度
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);

        // 如果左子树的高度大于右子树，说明最后一个节点一定在左子树, 如果高度相等，一定在右子树（完全二叉树）
        if(leftHeight > rightHeight) {
            return process(root.left);
        } else {
            return process(root.right);
        }
    }

    // 因为是完全二叉树，必定有左子节点，不一定有右子节点
    private int getHeight(Node node) {
        int level = 0;
        while(node != null) {
            level++;
            node = node.left;
        }
        return level;
    }


    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int val) {
            this.val = val;
        }
    }



}
