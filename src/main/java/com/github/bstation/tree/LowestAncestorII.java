package com.github.bstation.tree;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/7/18 19:10
 */
public class LowestAncestorII {

    /**
     * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
     *
     *  这里找的是二叉搜索树的最近公共祖先，所以可以通过二叉搜索树的特性来进行查找
     *
     *  二叉搜索树的特性：左子节点的值小于当前节点的值，右子节点的值大于当前节点的值
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
        node4.left = node2;
        node4.right = node6;
        node2.left = node1;
        node2.right = node3;
        node6.left = node5;
        node6.right = node7;

        System.out.println(getLowestAncestor(node4, node5, node6).value);
        System.out.println(getLowestAncestor2(node4, node5, node6).value);

    }

    static class Info {
        public boolean findA;
        public boolean findB;
        public Node ans;

        public Info(boolean findA, boolean findB, Node ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
    }

    // 递归解法  比二分查找慢
    public Node getLowestAncestor2(Node root, Node p, Node q) {
        return dfs(root, p, q).ans;
    }

    private Info dfs(Node root, Node p, Node q) {
        if(root == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = dfs(root.left, p, q);
        Info rightInfo = dfs(root.right, p, q);
        boolean findA = leftInfo.findA || rightInfo.findA || root == p;
        boolean findB = leftInfo.findB || rightInfo.findB || root == q;

        Node ans = null;
        if(leftInfo.ans != null) {
            ans = leftInfo.ans;
        } else if(rightInfo.ans != null) {
            ans = rightInfo.ans;
        } else {
            if(findA && findB) {
                ans = root;
            }
        }

        return new Info(findA, findB, ans);
    }


    public Node getLowestAncestor(Node root, Node p, Node q) {
        return process(root, p, q);
    }

    /**
     * 根据二叉树的特性进行比较
     */
    private Node process(Node root, Node p, Node q) {
        // 判断当前的两个节点是否属于不同的子树，如果是则当前节点就是公共子节点
        if(root.value <= Math.max(p.value, q.value) && root.value >=  Math.min(p.value, q.value)) {
            return root;
        }
        Node common = null;
        // 如果两个节点都位于左子树
        if(root.value > Math.max(p.value, q.value)) {
            common = process(root.left, p, q);
        } else {
            // 如果两个节点都位于右子树
            common = process(root.right, p, q);
        }
        return common;
    }


}
