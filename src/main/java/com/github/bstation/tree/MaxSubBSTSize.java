package com.github.bstation.tree;

/**
 * @author tangsong
 * @date 2021/5/22 16:07
 */
public class MaxSubBSTSize {

    /**
     * 最大搜索二叉子树的大小
     *  给定一棵二叉树的头结点head,返回这颗二叉树中最大的二叉搜索子树的头结点
     *
     *  什么是二叉搜索树：左子节点都小于头结点，右子节点都大于头结点
     *
     * 树形DP问题
     *
     */

    /**
     * 构建需要问子树要的信息
     */
    public static class Info {
        public boolean isAllBST; // 是否为二叉搜索树
        public int maxSubBSTSize; // 二叉搜索树的最大节点数目
        public int max; // 二叉树的最大值
        public int min; // 二叉树的最小值

        public Info(boolean isAllBST, int maxSubBSTSize, int max, int min) {
            this.isAllBST = isAllBST;
            this.maxSubBSTSize = maxSubBSTSize;
            this.max = max;
            this.min = min;
        }
    }

    /**
     * 1. 分为2中情况： 包含X节点，不包含X节点
     * 2. 需要问子树要什么信息： 是否为所有都是二叉搜索树，二叉树的最大值、最小值、最大二叉子树的大小
     * 3. 递归子树，每棵子树返回相同的信息
     *
     */
    public int maxSubBSTSize(Node head) {

        return process(head).maxSubBSTSize;
    }

    /**
     * 返回整颗树的信息，因为是递归，并且每颗树的信息都相同
     *  1. 递归获取每颗子树的信息
     *  2. 构建每颗树的信息并返回
     *
     */
    private Info process(Node head) {
        if(head == null) {
            // 因为递归到叶子节点时，节点的默认信息不是很好处理，返回null，和返回默认信息是相同的，只不过返回null，需要在使用的时候额外处理null
            return null;
        }

        // 获取到左右子树的信息
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        // 构建对应的数据，并将当前节点的数据返回  boolean isAllBST, int maxSubBSTSize, int max, int min

        int max = head.value;
        int min = head.value;
        if(leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
        }
        if(rightInfo != null) {
            // 右子树后赋值，那么需要比较一个最大值出来
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }

        int maxSubBSTSize = 0;

        // 获取到最大子树的大小, 从左右子树中选择最大的搜索子树
        if(leftInfo != null) {
            maxSubBSTSize = Math.max(maxSubBSTSize, leftInfo.maxSubBSTSize);
        }
        if(rightInfo != null) {
            maxSubBSTSize = Math.max(maxSubBSTSize, rightInfo.maxSubBSTSize);
        }

        boolean isAllBST = false;
        // 判断当前是否符合二叉搜索树
        /// 1. 左子树是否为搜索二叉树，右子树是否为搜索二叉树， 左子树的最大节点 > 当前节点 && 右子树的最小节点 < 当前节点
        // 只有当上述条件满足，包含当前节点的搜索二叉子树才可以是二叉搜索树，否则当前节点就不是一个二叉搜索子树，最大二叉搜索子树的大小就不能重新
        // 计算，只能是之前的更小子树的大小
        if(
                (leftInfo == null ? true : leftInfo.isAllBST)
                &&
                (rightInfo == null ? true : rightInfo.isAllBST)
                &&
                (leftInfo == null ? true : leftInfo.max < head.value)
                &&
                (rightInfo == null ? true : rightInfo.min > head.value)
        ) {
            // 如果包含当前节点的话仍然是二叉搜索熟，那么需要重新计算二叉搜索树的最大节点数
            maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize) + (rightInfo == null ? 0 : rightInfo.maxSubBSTSize) + 1;
            isAllBST = true;
        }

        return new Info(isAllBST, maxSubBSTSize, max, min);
    }


}
