package com.github.bstation.tree;

/**
 * @author tangsong
 * @date 2021/5/22 15:06
 */
public class IsBalancedTree {
    /**
     * 二叉树的递归套路：
     *  1、假设以X节点为头，假设可以向X左树和X右树要任何信息
     *  2、在上一步的假设下，讨论以X为头结点的树，得到答案的可能性(重要) -> 一般而言可以分为X参与或者X不参与两种选择
     *  3、列出所有的可能性后，确定到底需要向左树和右树要什么样的信息
     *  4、把左树和右树的信息求全集，就是任何一棵子树都需要返回的信息S
     *  5、递归函数都返回S，每一颗数都这么要求
     *  6、写代码，把代码中考虑如何把左树的信息和右树的信息整合出整棵树的信息
     *
     */

    /**
     * 是否平衡二叉树
     *  给定一个树的头结点，判断这棵树是否为平衡二叉树
     *
     *  平衡二叉树：左右子树的高度差不超过1
     *
     */

    /**
     * 定义对象，表示需要子树提供的信息
     */
    public static class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }


    /**
     * 1. 需要问左右子树要什么信息： 子树是否为平衡二叉树，子树的高度
     * 2. 递归获取左右子树的信息，并且每一颗子树返回的信息都相同
     *
     */
    public boolean isBalanced(Node head) {

        return process(head).isBalanced;
    }

    private Info process(Node head) {
        if(head == null) {
            // 如果递归到空节点，则默认该节点是平衡二叉树，并且高度为0
            return new Info(true, 0);
        }

        // 获取到左右子树的信息（每一个子树）
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        // 当前节点的高度
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        boolean isBalanced = false;
        // 判断子树是否为平衡二叉树
        if(leftInfo.isBalanced && rightInfo.isBalanced && Math.abs(leftInfo.height - rightInfo.height) <= 1) {
            isBalanced = true;
        }

        return new Info(isBalanced, height);
    }

}
