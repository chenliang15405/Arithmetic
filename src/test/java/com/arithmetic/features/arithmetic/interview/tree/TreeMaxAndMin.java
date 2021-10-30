package com.arithmetic.features.arithmetic.interview.tree;

import org.junit.Test;

/**
 * 给定一个树的头结点，需要返回这个树的最大值和最小值
 *
 * @author tangsong
 * @date 2021/2/28 20:17
 */
public class TreeMaxAndMin {

    /**
     * 思路：
     *  1. 获取当前头结点的左子树的最大值和最小值
     *  2. 获取当前头结点的右子树的最大值和最小值
     *  3. 比较左子树的最大值、右子树的最大值和当前值获取到整棵树的最大值，比较左子树的最小值、右子树的最小值和当前值获取到整棵树的最小值
     *  4. 递归每个节点，就可以获取到每个节点的包含子树的最小值和最大值，最终就可以获取到整棵树的最小值和最大值
     *  5. 当递归到叶子节点时，如果左子树和右子树都为空，那么该子树的最大值设置为系统最小，最小值设置为系统最大，这样比较下来之后，最大值和最小值都是这个叶子节点自己
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
        public int max;
        public int min;

        public ReturnData(int max, int min) {
            this.max = max;
            this.min = min;
        }
    }

    @Test
    public void test() {

    }

    public void printMaxAndMin(Node head) {
        ReturnData data = process(head);

        System.out.println("max=" + data.max);
        System.out.println("min=" + data.min);
    }


    public ReturnData process(Node head) {
        if(head == null) {
            // 最大值给系统最小，最小值给系统最大
            // 这样在叶子节点进行比较的时候，最大值和最小值都是叶子节点本身，不影响整个树的节点值的比较
            return new ReturnData(Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        ReturnData leftReturnData = process(head.left);
        ReturnData rightReturnData = process(head.right);
        int max = Math.max(Math.max(leftReturnData.max, rightReturnData.max), head.value);
        int min = Math.min(Math.min(leftReturnData.min, rightReturnData.min), head.value);

        return new ReturnData(max, min);
    }





}
