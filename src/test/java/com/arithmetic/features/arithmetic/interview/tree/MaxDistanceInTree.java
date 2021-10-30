package com.arithmetic.features.arithmetic.interview.tree;

/**
 * 二叉树中节点的最远距离
 *
 * 二叉树中，一个节点可以往上走和可以往下走，那么从A节点总能走到B节点，节点A到节点B的距离为：A走到B最短路径上节点的个数
 * 求一棵二叉树上的最远距离
 *
 *
 * 高度一致题目：（思路基本相同）都是递归行为
 *  TreeMaxAndMin
 *
 * @author tangsong
 * @date 2021/2/28 20:40
 */
public class MaxDistanceInTree {

    /**
     * 思路:
     *  1.最远的距离有三种情况：左子树中有最大距离、右子树中有最大距离、左子树的几点经过当前节点到右子树中有最大距离
     *  2.所以可以获取到左子树的最大距离和深度、右子树的最大距离和深度、和加上当前节点的最大距离和深度进行比较，哪个大，哪个就是最大距离
     *  3.在遇到叶子节点的子树为空的情况，最大深度和距离都是0，那么这样将当前节点向上返回的时候，就是最大深度为1，距离为1
     *
     *
     * 列出可能性，整合信息，返回结果
     *
     */
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class ReturnData {
        public int maxDistance;
        public int maxHeight;

        public ReturnData(int maxDistance, int maxHeight) {
            this.maxDistance = maxDistance;
            this.maxHeight = maxHeight;
        }
    }


    public int maxDistance(Node head) {
        ReturnData data = process(head);
        return data.maxDistance;
    }

    /**
     * 树的DP, 计算最大距离
     *
     */
    public ReturnData process(Node head) {
        if(head == null) {
            return new ReturnData(0, 0);
        }
        ReturnData leftReturnData = process(head.left);
        ReturnData rightReturnData = process(head.right);
        // 加上当前节点的距离 = 左树的高度 + 右树的高度 + 1
        int includeHeadDistance = leftReturnData.maxHeight + 1 + rightReturnData.maxHeight;
        // 计算出当前节点的最大高度 = 左树或右树的高度 + 1
        int curMaxHeight = Math.max(leftReturnData.maxHeight, rightReturnData.maxHeight) + 1;

        // 最大距离，要不在左树，要不在右树， 或者是加上当前节点的距离
        int maxDistance = Math.max(Math.max(leftReturnData.maxDistance, rightReturnData.maxDistance), includeHeadDistance);

        return new ReturnData(maxDistance, curMaxHeight);
    }


}
