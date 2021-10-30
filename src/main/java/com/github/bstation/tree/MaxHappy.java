package com.github.bstation.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author tangsong
 * @date 2021/5/22 16:28
 */
public class MaxHappy {

    /**
     *  给定一个多叉树
     *
     *  每个节点表示一个员工，树的层级表示员工的职位，父节点表示子节点的直接上级，每个节点中保存的是员工的活跃度，当一个员工的直接上级到场，
     *  那么这个员工就不会到场，当一个直接上级不到场，这个员工可能到场也可能不到场，取决于是否发邀请函，怎么发邀请函才能让活跃度最高
     *
     *  二叉树的递归DP套路
     *
     */

    public static class Employee {
        public int happy;  // 当前节点的活跃度
        public List<Employee> nexts;  // 当前节点的所有子节点

        public Employee(int happy) {
            this.happy = happy;
            nexts = new ArrayList<>();
        }
    }

    public static class Info {
        public int yes; // 到场的活跃度
        public int no; // 不到场的活跃度

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    /**
     * 和二叉树的DP相同的套路
     *
     *   1. 需要问子树要什么样的信息： 每个子树需要给 到场的活跃度、不到场的活跃度
     *   2. 递归获取每个子树的数据，并且每棵树返回的信息结构相同
     *
     */
    public int maxHappy(Employee boss) {
        Info info = process(boss);
        // 判断当场的活跃度和不到场的活跃度最大值
        return Math.max(info.yes, info.no);
    }

    private Info process(Employee head) {
        // base case 当节点为空则停止，表示到了最下层节点
        if(head == null) {
            // base case  为空则默认都是0
            return new Info(0, 0);
        }

        // 当前节点到场的活跃度初始化就等于当前的节点的活跃度
        int yesHappy = head.happy;
        int noHappy = 0;
        // 获取到当前所有子节点，并计算到场和不到场的活跃度
        for (Employee node : head.nexts) {
            // 判断子节点到场的活跃度和不到场的活跃度
            Info info = process(node);

            // 当前节点到场的活跃度 = 子节点不到场的活跃度 + 当前节点的活跃度
            yesHappy += info.no;
            // 当前节点不到场的活跃度 = 子节点不到场的活跃度 和 子节点到场的活跃度 中取最大的
            // 因为当前节点不到场，那么子节点可以到也可以不到
            noHappy += Math.max(info.yes, info.no);
        }

        return new Info(yesHappy, noHappy);
    }


}
