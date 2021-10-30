package com.github.bstation.tree;

/**
 * @author tangsong
 * @date 2021/6/14 16:43
 */
public class LowestAncestor {

    /**
     * 给定一棵二叉树的头节点head，和另外两个节点a和b
     * 返回a和b的最近公共祖先
     *
     * 二叉树的树形dp套路
     *
     */

    /**
     * 二叉树的树形dp套路
     *
     *    1. 将整体的问题分为与X节点有关，与X节点无关开始整理问题
     *    2. 问左树要信息、问右树要信息
     *    3. 整合左树和右树的信息，同样当前节点也返回自己的同样的信息
     *    4. 递归执行即可，因为所有的子树都是相同的内容
     *
     */

    /**
     * 左树和右树提供的信息
     */
    public static class Info {
        public boolean findA; // 是否找到节点A
        public boolean findB; // 是否找到节点B
        public Node ans; // A和B的最近公共节点

        public Info(boolean findA, boolean findB, Node ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
    }


    public Node lowestAncestor(Node head, Node a, Node b) {
        return process(head, a, b).ans;
    }


    /**
     * 递归过程
     */
    public Info process(Node X, Node a, Node b) {
        if(X == null) {
            // 当空节点时的默认值
            return new Info(false, false, null);
        }
        // 左右子树的信息
        Info leftInfo = process(X, a, b);
        Info rightInfo = process(X, a, b);
        // 构建当前节点的信息
        boolean findA = X == a || leftInfo.findA || rightInfo.findA;
        boolean findB = X == b || leftInfo.findB || rightInfo.findB;
        Node ans = null;
        // 左子树是否已经找到公共节点
        if(leftInfo.ans != null) {
            ans = leftInfo.ans;
        } else if(rightInfo.ans != null) {
            // 右子树是否已经找到最近公共子节点
            ans = rightInfo.ans;
        } else {
            // 如果左右子树都没有找到公共节点，那么判断是否A节点和B节点都找到了，如果A和B都找到了，那么当前节点必定是公共节点
            // 因为左右子树都没有公共节点，那么A和B肯定不在同一颗子树上，所以是刚找到的节点，所以当前节点是公共节点
            if(findA && findB) {
                ans = X;
            }
        }

        return new Info(findA, findB, ans);
    }


}
