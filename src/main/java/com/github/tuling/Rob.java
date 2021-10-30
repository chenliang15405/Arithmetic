package com.github.tuling;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/5/5 19:48
 */
public class Rob {

    /**
     * 打家劫舍1
     *    在一个数组中，选择可以获取到的最大的数据之和，不能选择相邻的数据，选择到的最大值是多少
     *
     *  1. 递归方式求解
     *
     *  2. 动态规划求解
     *
     *    动态规划三要素：
     *        1. 重叠子问题
     *        2. 最优子结构
     *        3. 递推公式（状态转移方程）： 根据一个值可以递推出来其他的值
     *
     */

    @Test
    public void test() {
        int[] nums = new int[]{114,117,207,117,235,82,90,67,143,146,53,108,200,91,80,223,58,170,110,236,81,90,222,160,165,195,187,199,114,235,197,187,69,129,64,214,228,78,188,67,205,94,205,169,241,202,144,240};

        // 递归
        System.out.println(maxMoney(nums, nums.length - 1));

        // 动态规划
        System.out.println(maxMoney1(nums));

        // 动态规划优化
        System.out.println(maxMoney2(nums));

        System.out.println(rob1(nums));
    }

    // 记忆化搜索
    public int rob1(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        Arrays.fill(dp, -1);
        return process(nums, nums.length - 1, dp);
    }

    // 某个位置需要获取到最大值，那么有两种可能，1. 偷当前位置和i-2的位置之和  2. i-1的位置   两种情况比较最大值
    private int process(int[] nums, int index, int[] dp) {
        if(index < 0) {
            return 0;
        }
        if(dp[index] != -1) {
            return dp[index];
        }
        // 如果到第0个位置，那么只有一个数可以获取
        if(index == 0) {
            dp[index] = nums[0];
            return nums[0];
        }
        int p1 = process(nums, index - 2, dp) + nums[index];
        int p2 = process(nums, index - 1, dp);
        int max = Math.max(p1, p2);
        dp[index] = max;

        return max;
    }


    /**
     * 递归方式
     *
     */
    private int maxMoney(int[] nums, int index) {
        if(nums == null || index < 0) {
            return 0;
        }
        // 当索引为0的时候，只有一个值可以获取，则直接返回0位置上的元素
        if(index == 0) {
            return nums[0];
        }
        // 递归获取最大值，有两种选择的方式，一种是选择index-1，一种是选择index-2，如果选择index-2那么可以加上index的值
        // 在递归的过程中，两种选择都需要获取到最大值，那么就需要在每次递归的时候都做相同的选择，选择index-1还是index-2这样的选择
        // 所以递归的计算方式都是相同的，从index-1和index-2+index的值中选择最大的元素作为当前的选择的可能性
        return Math.max(maxMoney(nums, index - 1), maxMoney(nums, index - 2) + nums[index]);
    }

    /**
     * 动态规划
     *  在递归中有多个重复计算的子过程，有两种优化的方式：1. 备忘录 2. 动态规划
     *    动态规划将每个位置选择的最优解已经保存到dp数组了，所以在做选择的时候，不用每次都递归去计算当前选择的位置的最大值
     *    直接从dp数组中获取指定位置的值，那个值就是该位置的最大值，因为在计算的过程已经将每个位置的最优解填充到dp数组
     *
     *    通过递推公式，将已经确定的值递推出没有计算出来的值，填充dp数组
     *
     *  1. 重叠子问题
     *  2. 最优子结构
     *  3. 递推公式（状态转移方程）
     *
     */
    private int maxMoney1(int[] nums) {
        if(nums == null || nums.length < 0) {
            return 0;
        }
        if(nums.length == 0) {
            return nums[0];
        }
        // 构建dp数组，当前问题中，只有一个可变因素选择的位置索引，所以只需要构建一维数组，因为位置如果确定，那么就可以确定当前的最大值，不需要依赖其他的变量因素
        int[] dp = new int[nums.length];
        // 初始化dp数组，填充已知的数据
        dp[0] = nums[0]; // 0位置的最优解（最大值），就是数组的0索引的数据
        dp[1] = Math.max(nums[0], nums[1]); // 当选择1索引时，最优解有两种选择，一种选择0索引的位置，一种是选择1位置的数据，不能选择相邻的数据

        // 开始填充每个位置的最优解
        for (int i = 2; i < nums.length; i++) {
            // 根据递推公式，获取到每个位置的最优解
                // 有两种选择，选择前一个位置 或者 选择前2个位置+当前位置的数据， 从其中选择最大的值作为最优解，因为每个位置都保存的是最优解
                // 所以无需进行递归，直接从dp数组中取该位置的数据即为已经计算完成的最优解（最大值）的情况
            dp[i] = Math.max(dp[i - 1], dp[i-2] + nums[i]);
        }

        // 计算最终的位置的最优解
        return dp[nums.length-1];
    }


    /**
     * 动态规划优化
     *   将O(N)的空间复杂度优化为O(1)的空间复杂度
     *
     */
    private int maxMoney2(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        if(nums.length == 1) {
            return nums[0];
        }
        // 不创建dp数组，只需要两个变量
        int first = nums[0];
        int second = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            // 在当前值+(n-2) 和 n-1的值 进行比较，获取最大值，计算出来的值就是当前i对应的最优解
            int temp = Math.max(first+nums[i], second);
            // 将(n-1)赋值给first变量，作为下一轮循坏的n-2
            first = second;
            // 将当前计算出来的i位置的最优解，赋值给second，作为下一轮的n-1
            second = temp;
        }
        // 最后的second就是最后一个i位置的最优解
        return second;
    }


    /**
     * 打家劫舍2
     *      如果数组中的首尾是相连的，那么如何获取到最大的值
     *
     */
    @Test
    public void test2() {
        int[] nums = new int[]{2,7,9,3,1};

        System.out.println(maxMoneyProcess(nums));
    }


    private int maxMoneyProcess(int[] nums) {
        int lMax = maxMoneyWithCircle(nums, 0, nums.length - 2);
        int rMax = maxMoneyWithCircle(nums, 1, nums.length - 1);
        return Math.max(lMax, rMax);
    }

    /**
     * 如果首尾相连获取到最大值的情况，那么第一个和最后一个不能同时选择，和之前的方式不太一样
     *  有两种方式：
     *   1、取第一个，那么最后一个就不能取
     *   2、 取最后一个，那么第一个就不能取
     *
     * 通过定义变量，让计算最大值的方式能够通用
     *
     */
    private int maxMoneyWithCircle(int[] nums, int start, int end) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        if(nums.length == 1) {
            return nums[0];
        }

        int first = nums[start];
        // 计算第二个位置上的最优解
        int second = Math.max(nums[start], nums[start+1]);

        for (int i = start + 2; i <= end; i++) {
            int temp = Math.max(first + nums[i], second);
            // 将当前的n-1赋值给first, 作为下一轮的n-2
            first = second;
            // 将当前计算出来的i的最优解作为下一轮的n-1
            second = temp;
        }
        return second;
    }


    public static class Node {
        public Integer val;
        public Node left;
        public Node right;

        public Node(Integer val) {
            this.val = val;
        }
    }

    /**
     * 打家劫舍3
     *    当数组的数据结构为二叉树的方式，并且不能同时选择直接相连的节点，那么选择的最大值是多少
     *
     */

    @Test
    public void test3() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);

        node1.left = node2;
        node2.left = node4;
        node2.right = node5;
        node1.right = node3;
        node3.left = node6;
        node6.left = node7;

        System.out.println(maxMoneyDfs(node1));
    }



    private int maxMoneyDfs(Node root) {
        int[] dfs = dfs(root);
        return Math.max(dfs[0], dfs[1]);
    }

    /**
     * DFS 解法
     *   通过定义数组，0位置表示当前值被选择时最大值，1位置表示当前值没有被选择时最大值
     *
     *  最后通过比较选择和没有被选择的最大值 就是最终解
     *
     */
    private int[] dfs(Node root) {
        // {select最优解，not select最优解}
        if(root == null) {
            return new int[]{0, 0};
        }
        // 计算左子树的被选择、不被选择的最优解 、右子树被选择、不被选择的最优解
        int[] l = dfs(root.left);
        int[] r = dfs(root.right);
        int selectMax = root.val + l[1] + r[1]; // 当前值 + 左右子节点都没有被选择的值  作为被选择的最大值
        int noSelectMax = Math.max(l[0], l[1]) + Math.max(r[0], r[1]); // 当前节点不被选择，那么左子节点和右子节点可以选择，也可以不选择，要看选择最大还是不被选择最大

        return new int[]{selectMax, noSelectMax};
    }

}
