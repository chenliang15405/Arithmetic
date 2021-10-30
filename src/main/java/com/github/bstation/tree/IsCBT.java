package com.github.bstation.tree;

/**
 * @author tangsong
 * @date 2021/6/14 16:20
 */
public class IsCBT {

    /**
     * 给定一个二叉树头结点，判断当前的二叉树是否为完全二叉树
     *
     *  树形DP套路
     *
     */

    /**
     * 左树和右树提供的信息
     */
    public static class Info {
        public boolean isFull; // 是否满二叉树
        public boolean isCBT;  // 是否完全二叉树
        public int height;  // 二叉树的高度

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    public boolean isCBT(Node head) {
        return process(head).isCBT;
    }

    /**
     * 判断二叉树是否为完全二叉树，有四种判断可能情况：
     *  1. 左子树满二叉树、右子树满二叉树，左子树的高度==右子树的高度
     *  2. 左子树为完全二叉树，右子树为满二叉树，左子树的高度==右子树的高度+1
     *  3. 左子树满二叉树，右子树为满二叉树，左子树的高度==右子树的高度+1
     *  4. 左子树满二叉树，右子树为完全二叉树，左子树的高度==右子树高度
     *
     */
    public Info process(Node X) {
        // 当节点为空的情况，默认的值
        if(X == null) {
            // 默认值
            return new Info(true, true, 0);
        }
        // 获取左树和右树的信息
        Info leftInfo = process(X.left);
        Info rightInfo = process(X.right);

        // 还需要构建当前节点所在二叉树的信息

        // 左子树是满二叉树并且右子树是满二叉树, 并且高度还要相同，否则可能不是满二叉树
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        // 最大的高度 + 当前节点， 就是总的高度
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isCBT = false;
        // 判断当前是否为完全二叉树
        if(leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
            isCBT = true;
        } else if(leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        } else if(leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
            isCBT = true;
        } else if(leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
        }

        return new Info(isFull, isCBT, height);
    }




}
