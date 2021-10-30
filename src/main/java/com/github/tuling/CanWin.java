package com.github.tuling;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/5/3 19:39
 */
public class CanWin {

    /**
     *  预测赢家
     *    给定一个数组，数组中的每个元素表示分数，玩家1和玩家2进行选择，每个人只能选择两边的分数，依次进行选择，
     *
     */

    @Test
    public void test() {
//        int[] arr = new int[]{5,200,3,5,3};  // false
        int[] arr = new int[]{5,200,3,5};   // true

        // 递归
        boolean b = canWin(arr);
        System.out.println(b);

        // 递归优化
        int i = maxScore1(arr, 0, arr.length - 1);
        System.out.println("先手赢： " + (i > 0));

        // dp
        System.out.println(maxScoreWithDp(arr));
    }

    @Test
    public void test1() {
        int[][] arr = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println(Arrays.toString(arr[1]));
        System.out.println(arr[1][2]);
    }


    /**
     * 判断玩家1是否赢
     *
     */
    private boolean canWin(int[] arr) {
        // 计算所有的总分数
        int sum = Arrays.stream(arr).sum();

        int p1 = maxScore(arr, 0, arr.length - 1);

        return p1 > sum - p1;
    }


    /**
     * 计算玩家1的最大分数
     *
     *  递归算法
     *
     * 分为两种情况：玩家1 先选择最左边 或 最右边
     *    选择最左边： 那么剩下选择两种可能选择项中，只能获取到其中最小的总分数了，因为玩家2会选择可能性中加起来最大的总分数
     *    选择最右边： 那么剩下选择的两种可能生成的总分数，只能获取到其中最小的总分数
     *
     *  从 选择最左边的总分数、选择最右边的总分数中挑选出最大的总分数作为玩家1的最大选择总分数
     *
     *  不能直接贪心，贪心的思想是每次局部获取最大的，达到总体是最大的，但是这个场景并不适用
     */
    private int maxScore(int[] arr, int l, int r) {
        if(l == r) {
            return arr[l];
        }
        // 如果只有2个数
        if(r - l == 1) {
            return Math.max(arr[l], arr[r]);
        }
        int sLeft = 0;
        int sRight = 0;

        if(r - l >= 2) {
            // 判断两种可能的得分
            // 1.如果先选择左边的分数
                // Math.min() 中计算的是在玩家2选择之后，玩家1的两种可能性选择分数的两种情况中的最小值，因为玩家2不会给玩家1留下较大的分数，它会自己选择较大的分数，然后将较小的分数留下来
            sLeft = arr[l] + Math.min(maxScore(arr, l + 2, r), maxScore(arr, l + 1, r - 1));
            // 同样的，先选择右边，那么剩下的数中，玩家2会选择较大的分数，然后将两种可能中计算得到的较小的总分数留下来给玩家1
            sRight = arr[r] + Math.min(maxScore(arr, l, r - 2), maxScore(arr, l + 1, r - 1));
        }
        return Math.max(sLeft, sRight);
    }


    /**
     * 优化版本1，
     *  计算先手和后手选择分数的差值，当先手选择之后，可以计算出来在剩余的区间中，后手可以得到的最大分数值， 然后进行相减，
     *  如果>0 则表示先手可以赢，如果小于0，则表示后手可以赢
     *
     */
    private int maxScore1(int[] arr, int l, int r) {
        if(l == r) {
            return arr[l];
        }
        // 如果先获取左边，那么计算先手和后手的分数差值
        int sLeft = arr[l] - maxScore1(arr, l+1, r);
        // 如果先获取右边，那么计算先手和后手的分数差值
        int sRight = arr[r] - maxScore1(arr, l, r-1);

        // 计算两种可能中最大的分数，作为选择的可能性中最大的分数，如果大于0则先手赢，如果小于0则后手赢
        return Math.max(sLeft, sRight);
    }



    /**
     * dp数组中保存的是，每个位置保存的是从数组的l位置到r位置，先手和后手的得分的差值，先手获取到的分数 - 后手在剩下的区间中获取到的最大分数，得到的就是
     *  两个选手选择的最终分数的差值，如果为正则表示 先手可以赢，如果为负，则表示后手可以赢，  后手在剩下的区间中获取到的最大分数 -> 是指在分数区间中，
     *  后手经过每次选择之后，和先手在剩余区间得到的分数的差值之后的最大值，并不是直接选择一个最大的，而是还是需要递归，进行选择，进行计算，最终获取到剩下的
     *  区间中分数差值最大的。
     */

    /**
     * 动态规划，减少重复计算的子过程
     *   构建dp数组，数组中的每个位置表示在l和r位置之间分数差，通过填充dp数组，最终通过获取到dp数组中的位置判断先手是否可以赢
     *
     * 动态规划的版本就是根据上面的递归的版本优化而来，总体的逻辑和上面的递归版本的逻辑保持一致
     *
     */
    private boolean maxScoreWithDp(int[] arr) {
        // 构建dp数组
        // l是行，r是列
        int[][] dp = new int[arr.length][arr.length];
        // 填充初始的dp数组，因为当l==r的时候，数值是固定的
        for (int i = 0; i < arr.length; i++) {
            // 当l和r相等时候，只能有一个固定的值
            dp[i][i] = arr[i];
        }
        // 一般dp都for循环，不会递归，因为目的是填充dp数组，并且利用已有的dp数组的值进行计算
        // l等于arr.length-2，因为这个循环表示先手进行选择的循环，当先手先进行选择，选择到length-2时，剩下的就是后手进行选择并获取到剩下区间的最大的分数值差，最多到length-1
        for (int l = arr.length - 2; l >= 0; l--) {
            for (int r = l+1; r < arr.length; r++) {
                // 内部的逻辑就是上面的递归的逻辑，只不过将递归的过程替换为填充dp数组的过程
                // dp数组中获取的就是当先手选择其中一个分数之后，剩下的l-r的区间中先手和后手接下来选择的最大的分数差，那么使用当前选择的分数减去接下来的区间的最大分数差，
                // 那么就可以得到当前选择的情况下的最大分数差，并填充到dp数组，这里填充的是l-r的区间中最大的分数差，是从最小的区间分数差开始逐渐填充到最大的区间分数差，而
                // 结果需要的就是0-arr.length-1的区间中的最大分数差
                dp[l][r] = Math.max(arr[l] - dp[l+1][r], arr[r] - dp[l][r-1]);
            }
        }
        // 需要返回的最大分数差的区间就是0——arr.length-1的区间中最大的分数差
        return dp[0][arr.length-1] >= 0;
    }


}
