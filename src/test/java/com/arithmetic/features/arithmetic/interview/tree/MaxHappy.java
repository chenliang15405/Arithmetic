package com.arithmetic.features.arithmetic.interview.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个多叉树
 *
 * 每个节点表示一个员工，树的层级表示员工的职位，父节点表示子节点的直接上级，每个节点中保存的是员工的活跃度，当一个员工的直接上级到场，那么这个员工就不会
 * 到场，当一个直接上级不到场，这个员工可能到场也可能不到场，取决于是否发邀请函，怎么发邀请函才能让活跃度最高
 *
 *
 * 树形DP
 *  高度一致题目：（思路基本相同）都是递归行为
 *    TreeMaxAndMin
 *    MaxDistanceInTree
 *
 * @author tangsong
 * @date 2021/2/28 21:03
 */
public class MaxHappy {

    /**
     * 思路：
     *   1. 当前节点来的活跃度：所有子节点不来时的活跃度之和 + 当前节点的活跃度
     *   2. 当前节点不来的活跃度： 所有的子节点中，每个子节点来或者不来的活跃度中选择最大的活跃度 之和
     *   3. 对当前节点来的活跃度和不来的活跃度进行比较，获取最大的活跃度
     *
     */

    // 定义多叉树的节点
    public static class Node {
        public int huo;  // 当前节点的活跃度
        public List<Node> nexts;  // 当前节点的所有子节点

        public Node(int huo) {
            this.huo = huo;
            nexts = new ArrayList<>();
        }
    }

    public static class ReturnData {
        public int lai_huo;
        public int bu_lai_huo;

        public ReturnData(int lai_huo, int bu_lai_huo) {
            this.lai_huo = lai_huo;
            this.bu_lai_huo = bu_lai_huo;
        }
    }

    /**
     * 返回最大活跃度
     *
     */
    public int maxHappy(Node head) {
        ReturnData data = process(head);
        return Math.max(data.lai_huo, data.bu_lai_huo);
    }

    /**
     * 多叉树的递归过程
     *
     */
    public ReturnData process(Node head) {
        // 统计当前节点来的活跃度和不来的活跃度
        int lai_huo = head.huo;  // 如果head==null，那么lai_huo = 0
        int bu_lai_huo = 0;

        // 因为Node的初始化中包含子节点的实例化，所以此条件已经包含BaseCase，即当dp到叶子节点时，不会进行遍历，lai_huo和bu_lai_huo都是0
        for (int i = 0; i < head.nexts.size(); i++) {
            // 获取当前节点的每个子节点的活跃度对象并进行计算，通过递归的行为计算直接子节点的直接子节点
            Node next = head.nexts.get(i);
            // 获取到子树的活跃度对象（来的活跃度和不来的活跃度）
            ReturnData data = process(next);

            // 当前节点来的活跃度 = 当前节点的活跃度 + 子节点不来的活跃度
            lai_huo += data.bu_lai_huo;
            // 当前节点不来的活跃度 = 子节点来的活跃度 和 子节点不来的活跃度 之中取最大值
            bu_lai_huo += Math.max(data.lai_huo, data.bu_lai_huo);
        }
        return new ReturnData(lai_huo, bu_lai_huo);
    }



}
