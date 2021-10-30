package com.github.bstation.tree;

/**
 * @author tangsong
 * @date 2021/7/18 18:08
 */
public class MaxTreeDeep {

    /**
     * 二叉树的最大深度
     *
     * 其实也是树型dp问题
     *
     *  1. 递归（dfs）
     *  2. bfs按照层序遍历计算
     *
     */


    public int maxDeep(Node root) {
        return process(root);
    }

    private int process(Node root) {
        if(root == null) {
            return 0;
        }
        // 问左子树要信息和右子树要信息
        int left = process(root.left);
        int right = process(root.right);
        // 组装当前节点的信息返回
        int deep = 0;
        if(left >= right) {
            deep = left + 1;
        } else {
            deep = right + 1;
        }

        return deep;
    }


}
