package com.github.bstation.tree;

/**
 * @author tangsong
 * @date 2021/5/22 15:36
 */
public class MaxDistance {

    /**
     * 最大距离（树形DP问题）
     *   给定一个二叉树的头结点Head, 任何两个节点之间都存在距离，返回整颗二叉树的最大距离
     */


    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    /**
     * 套路问题：
     *   1. 分为2类： 与头结点X有关、 与头结点无关
     *   2. 需要问左右两颗子树要什么信息： 1. 子树的最大距离、子树的最大高度
     *   3. 递归获取左右子树的信息，每一颗树都需要返回相同的信息
     *
     */
    public int maxDistance(Node head) {
        return process(head).maxDistance;
    }


    /**
     * 定义结构，表示需要问子树需要的信息，里面可以包含需要的任何信息
     *
     */
    private Info process(Node head) {
        // base case: 当递归到空节点，则默认返回0，0
        if(head == null) {
            return new Info(0, 0);
        }
        // 获取左子树的信息
        Info leftInfo = process(head.left);
        // 获取右子树的信息
        Info rightInfo = process(head.right);

        // 计算当前节点的高度(左子树或右子树的最大高度 + 1)
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        // 计算当前的最大距离（包含当前节点、不包含当前节点）
        int maxDistance = Math.max(
                Math.max(leftInfo.maxDistance, rightInfo.maxDistance),
                leftInfo.height + rightInfo.height + 1
        );
        return new Info(maxDistance, height);
    }
}
