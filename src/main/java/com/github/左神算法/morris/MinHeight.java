package com.github.左神算法.morris;

import com.github.bstation.tree.Node;
import javafx.util.Pair;
import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/8/14 13:29
 */
public class MinHeight {

    /**
     * 给定一棵二叉树的头结点head
     *  求以head为头的树中，最小深度是多少？
     *
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

        node1.left = node2;
        node2.left = node4;
        node2.right = node5;
        node1.right = node3;
        node3.left = node6;
        node6.left = node7;

        System.out.println(process(node1));
        System.out.println(process2(node1));
    }

    // 最小深度，是以整棵树为计算的，所以如果左子树为空，则使用右子树的高度，如果右子树为空则使用左子树高度
    public int process(Node root) {
        if(root.left == null && root.right == null) {
            return 1;
        }
        int leftH = Integer.MAX_VALUE;
        if(root.left != null) {
            leftH = process(root.left);
        }
        int rightH = Integer.MAX_VALUE;
        if(root.right != null) {
            rightH = process(root.right);
        }

        return Math.min(leftH, rightH) + 1;
    }

    public int process2(Node root) {
        if(root == null) {
            return 0;
        }
        int min = 0;
        if(root.left != null) {
            min = process2(root.left);
        }
        if(root.right != null) {
            min = Math.min(process2(root.right), min);
        }

        // 当叶子结点时，这里直接返回 0 + 1= 1
        return min + 1;
    }

}
