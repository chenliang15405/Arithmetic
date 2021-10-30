package com.github.左神算法.动态规划;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/7/11 13:02
 */
public class CoinsMinOnePaper {

    /**
     * arr是货币数组，其中的值都是正数，再给定一个整数aim
     * 每个值都认为是<b>一张货币</b>，返回组成aim的最少货币数
     * 注意：
     *   因为是求最少的货币数，所以每一张货币认为是相同或者不同就不重要了
     *
     */

    @Test
    public void test() {
        int[] arr = {5, 3, 4, 24, 12, 17, 18, 9, 11, 29, 6};
        int aim = 16;

        System.out.println(ways(arr, aim));
        System.out.println(dp(arr, aim));

    }


    /**
     * 暴力递归
     *
     */
    public int ways(int[] arr, int aim) {
        if(arr == null || arr.length <= 0 || aim == 0) {
            return 0;
        }
        return process(arr, aim, 0);
    }

    // 从index开始选择，组成aim的最少货币数量
    private int process(int[] arr, int aim, int index) {
        if(aim < 0) {
            return Integer.MAX_VALUE;
        }
        if(index == arr.length) {
            // 如果选择到最后一个，aim=0，那么则表示当前可以凑到aim，因为是统计最少货币数，所以不需要返回1作为有效方法数，货币已经在前面的调用过程中
            // 累计，Integer.MAX_VALUE作为无效标识
            return aim == 0 ? 0 : Integer.MAX_VALUE;
        }
        // 不要当前的货币，因为不要当前的货币，所以p1不需要对aim减去多少，让index+1取搞定aim的最少货币数，所以当前的货币
        // 是没有选择的，不需要加上当前的货币, 因为没   有要当前的货币，所以p1不存在判断后面返回的是-1或者有效的数字，直接向上返回即可，
        // 因为不需要判断后续是否可以搞定aim，如果可以搞定，则需要判断后续是否为有效的，因为判断有效才能加上当前的货币作为累计数
        int p1 = process(arr, aim, index + 1);
        // 如果要当前的货币，则下面的子过程只需要搞定aim-arr[index]的值就可以，如果选择当前的，下面的子过程无法搞定，那么就返回-1，
        // 认为当前的选择是无效的，不累加当前的货币数量，如果子过程可以搞定，那么子过程将它使用的货币数量返回，判断如果是有效的数据，就
        // 将当前选择的货币数量相加
        int p2 = process(arr, aim - arr[index], index + 1);
        if(p2 != Integer.MAX_VALUE) {
            // 因为是统计最少货币数量，所以在子过程可以搞定下面的数值的情况下，将当前的货币数量加上
            p2++;
        }
        // 在两种选择中选择最少使用的货币数量
        return Math.min(p1, p2);
    }


    public int dp(int[] arr, int aim) {
        if(arr == null || arr.length <= 0 || aim == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= aim; j++) {
                int p1 = dp[i + 1][j];
                // 如果要当前的货币，则下面的子过程只需要搞定aim-arr[index]的值就可以，如果选择当前的，下面的子过程无法搞定，那么就返回-1，
                // 认为当前的选择是无效的，不累加当前的货币数量，如果子过程可以搞定，那么子过程将它使用的货币数量返回，判断如果是有效的数据，就
                // 将当前选择的货币数量相加
                int p2 = Integer.MAX_VALUE;
                if(j - arr[i] >= 0) {
                    p2 = dp[i + 1][j - arr[i]];
                }
                if(p2 != Integer.MAX_VALUE) {
                    // 因为是统计最少货币数量，所以在子过程可以搞定下面的数值的情况下，将当前的货币数量加上
                    p2++;
                }
                dp[i][j] = Math.min(p1, p2);
            }
        }
        return dp[0][aim];
    }



    private Info getInfo(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        int N = map.size();
        int[] coins = new int[N];
        int[] counts = new int[N];
        int index = 0;
        // 填充到Info中
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            coins[index] = entry.getKey();
            counts[index] = entry.getValue();
            index++;
        }

        return new Info(coins, counts);
    }


    public static class Info {
        public int[] coins;
        public int[] counts;

        public Info(int[] coins, int[] counts) {
            this.coins = coins;
            this.counts = counts;
        }
    }
}
