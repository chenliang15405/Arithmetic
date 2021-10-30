package com.github.bstation;

import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author tangsong
 * @date 2021/6/11 21:55
 */
public class NumSquares {

    /**
     * 给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
     *
     * 给你一个整数 n ，返回和为 n 的完全平方数的 最少数量 。
     *
     * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
     *
     */

    @Test
    public void test() {
        int n = 13;

        System.out.println(getNum(n));

        System.out.println(getNumWithDp(n));

    }


    /**
     * BFS 解法，从小的值依次向大的数遍历，并每次都判断当前是否等于target
     *
     */
    public int getNum(int n) {
        // BFS的队列
        Queue<Integer> queue = new LinkedList<>();
        // 记录已经访问过的值，无需重复判断
        Set<Integer> visit = new HashSet<>();
        // 从0开始
        queue.offer(0);
        visit.add(0);
        // 记录最少的数
        int count = 0;
        while (!queue.isEmpty()) {
            // 获取当前需要进行对比的数，出来的数都是比n小的数，才可能加上其他的平方数得到最终值
            int size = queue.size();
            count++;
            // 遍历队列中的数
            for (int i = 0; i < size; i++) {
                // 当前值
                Integer cur = queue.poll();
                // 遍历1到n的所有数
                for (int j = 1; j < n; j++) {
                    // 当前的数之和, sum的值总是完全平方和的数，这里不是累加，而是使用cur每次加不同的完全平方和，所以将count放到外层进行++是合理的，因为每次遍历都只能走1步
                    int sum = cur + j * j; // 当前值 + 下一个数的完全平方和
                    // 判断当前数和目标值的大小
                    if(sum == n) {
                        // 直接返回，因为当前的count就是最小的
                        return count;
                    } else if(sum > n) {
                        // 如果大于则直接break，因为是依次增加的，所以无需遍历
                        break;
                    }
                    if(!visit.contains(sum)) {
                        // 如果当前值小于n，则需要记录已访问过，并且放入到队列中，下次可以从当前数开始遍历，判断是否可以得到目标值
                        // 每个小于N的数都可能加上其他的完全平方和数 = target
                        visit.add(sum);
                        queue.offer(sum);
                    }
                }
            }
        }
        return count;
    }



    public int getNumWithDp(int n) {
        // 因为只有1个因素，所以使用一维数组
        int[] dp = new int[n + 1];
        //dp[0] = 0
        // dp[i] 表示i的完全平方数之和的最少的个数

        for (int i = 1; i <= n; i++) {
            // 最差的情况是所有1
            dp[i] = i;
            // 因为取的是前一个完全平方数，所以需要>=0
            for (int j = 1; i - j * j >= 0; j++) {
                // 那就计算当前dp[i]的最少的个数和dp[i-j*j]+1的个数哪个最少
                // dp[i-j*j] 是 i-j*j位置的数的最少个数，因为上一个数需要到i之间也需要通过完全平方和到达
                // 所以 i-j*j -> 指的就是i的上一个完全平方数，并且还需要+1，因为上一个本身的数加当前到i的一个数
                dp[i] = Math.min(dp[i], dp[i - j*j] + 1);
            }
        }
        // 最终返回的就是第n个位置的数
        return dp[n];
    }

}
