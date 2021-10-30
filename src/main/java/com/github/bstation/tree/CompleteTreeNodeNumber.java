package com.github.bstation.tree;

/**
 * @author tangsong
 * @date 2021/5/30 19:20
 */
public class CompleteTreeNodeNumber {

    /**
     * 计算完全二叉树的节点的个数
     *
     * 要求时间复杂度低于O(N)
     */

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }


    /**
     * 计算完全二叉树的节点的个数
     *
     * 如果一个树是满二叉树，那么这个树的节点是 2^n - 1，n表示层数
     *
     * 计算完全二叉树的节点的个数，那么就先获取到树的最大层数，然后判断右子树的最大深度是否等于当前的最大深度，
     * 1. 如果等于：因为是完全二叉树，那么左子树一定是满二叉树，所以左子树的节点个数等于2^n - 1, 然后递归计算右子树（还是继续将右子树当做一棵完整的树（也是完全二叉树），然后计算层数和左子树的节点，然后继续递归）
     * 2. 如果不等于：那么右子树一定是一个小的满二叉树，所以右子树的节点个数为 2^(n-1) - 1 -> 肯定是少一层，然后递归计算左子树（和整颗树的计算方式一样，继续计算小左子树和小右子树）
     *
     */
    public int nodeNum(Node head) {
        if(head == null) {
            return 0;
        }
        // 先计算树的层高
        int high = mostLeftLevel(head, 1);
        return process(head, 1, high);
    }

    /**
     * 递归处理每个子树的节点信息
     *
     * level参数表示：head节点所在的层数，越向下，层数越大
     */
    private int process(Node head, int level, int high) {
        // base case
        if(level == high) {
            // 当递归的过程中，已经递归到最大深度了，那么肯定只有1个节点，因为是每半个子树进行递归的，最终只会到某一个子树的最深的节点
            // 不是最深节点的子树都可以直接使用公式计算出来节点数
            return 1;
        }

        // 判断右子树的最大深度是否等于当前树的最大深度, 因为计算的是右子树，所以当前的右子树的当前所在level就等于level+1(右子树的层数就是当前层数+1, 返回的是距离最大深度的层数)
        int rightLevel = mostLeftLevel(head.right, level + 1);
        if(rightLevel == high) {
            // 如果相等，那么就可以直接计算左子树的节点个数
            // 左子树的节点应该等于 2^(high - level) -1，需要加上当前节点的值，那么就再+1
            return (1 << (high - level)) + process(head.right, level + 1, high);
        } else {
            // 如果不相等，直接就可以计算右子树的节点个数
            // 右子树的节点个数就等于 2^(high - level - 1），右子树的最大深度不等于当前的最大深度，因为是完全二叉树，所以就是-1层，也是因为需要计算当前节点，所以+1和计算节点时的-1抵消
            return (1 << (high - level - 1)) + process(head.left, level + 1, high);
        }
    }

    /**
     * 根据当前的层，计算最大的层高
     */
    private int mostLeftLevel(Node head, int level) {
        while (head != null) {
            level++;
            head = head.left;
        }
        // 因为判断的是当前节点是否等于nul，那么就会多加一次，所以需要-1
        return level - 1;
    }

}
