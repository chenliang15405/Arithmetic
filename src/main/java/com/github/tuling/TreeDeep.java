package com.github.tuling;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author tangsong
 * @date 2021/5/2 19:24
 */
public class TreeDeep {

    /**
     *  计算二叉树的最小深度
     *    深度优先  -> 从叶子节点向上找，比较最小的深度大小
     *    广度优先  -> 从根节点向下找，先到叶子节点的则为最小深度
     */

    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public int deep; // 当前节点的深度

        public Node(int val) {
            this.val = val;
        }
    }

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

        System.out.println(getMinDeep(node1));

        System.out.println(getMinDeep2(node1));
    }

    /**
     * 最小深度  深度优先算法
     *
     */
    private int getMinDeep(Node root) {
        if(root == null) {
            return 0;
        }
        // base case  当前为叶子节点则终止
        if(root.left == null && root.right == null) {
            // 当前的节点为叶子节点时，则返回深度为1
            return 1;
        }
        // 定义最小深度，并计算左子树的深度和右子树的深度，并进行比较
        int min = Integer.MAX_VALUE;

        if(root.left !=  null) {
            // 计算左子树的最小深度
            min = Math.min(getMinDeep(root.left), min);
        }
        if(root.right != null) {
            // 计算右子树的最小深度
            min = Math.min(getMinDeep(root.right), min);
        }
        // 获取到最小深度，加上根节点的深度
        return min + 1;
    }


    /**
     * 计算树的最小深度  广度优先算法
     *
     */
    private int getMinDeep2(Node root) {
        if(root == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        root.deep = 1;
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            // 如果当前节点先到达叶子结点，则当前节点的深度就是最小深度，因为广度优先遍历，先达到叶子节点则表示深度最小
            if(node.left == null && node.right == null) {
                return node.deep;
            }
            if(node.left != null) {
                // 当前左子节点的树深度，等于父节点+1
                node.left.deep = node.deep + 1;
                queue.offer(node.left);
            }
            if(node.right != null) {
                // 当前右子节点的树深度，等于父节点+1
                node.right.deep = node.deep + 1;
                queue.offer(node.right);
            }
        }
        return 0;
    }



}
