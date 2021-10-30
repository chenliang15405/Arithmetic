package com.github.左神算法.对数器及数据量找规律;

import com.github.bstation.array.SubMatrixMaxSum;
import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/9/4 11:43
 */
public class MoneyProblem {

    /**
     *  根据数据规模决定不同解法的问题 （这道题也是动态规划的问题）
     *
     *  int[] d, d[i] : i号怪兽的能力
     *  int[] p, p[i] : i号怪兽要求的钱
     *  开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的怪兽。
     *  如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽，然后怪兽就会加入你，它的能力直接累加到你的能力上，
     *  如果你当前的能力，大于等于i号怪兽的能力，你可以选择直接通过，你的能力并不会下降，你也可以选择贿赂这个怪兽，然后怪兽就会加入你，
     *  它的能力直接累加到你的能力上。
     *  返回通过所有的怪兽，需要花的最小钱数
     *
     */
    @Test
    public void test() {
        int len = 10;
        int value = 20;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int[][] arrs = generateTwoRandomArray(len, value);
            int[] d = arrs[0];
            int[] p = arrs[1];
            long ans1 = func1(d, p);
            long ans2 = func2(d, p);
            long ans3 = func3(d, p);
            long ans4 = func4(d, p);
            if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
                System.out.println("ans1=" + ans1 + ", ans2=" + ans2 + ", ans3=" + ans3 + ", ans4=" + ans4);
                System.out.println("oops!");
            }
        }
    }


    /**
     * 返回最小花的钱数
     *
     */
    public long func1(int[] d, int[] p) {
        return process1(d, p, 0, 0);
    }

    /**
     * 递归 （从左向右尝试模型）
     *  第一种解法， 以能力作为判断标准 （适用于怪兽能力数值不超过10^8, 因为如果太大会超过执行时间）
     *
     */
    private long process1(int[] d, int[] p, int index, int ability) {
        if(index == d.length) {
            return 0; // 如果通过所有怪兽，返回0，因为钱数在递归之前已经加过了
        }
        // 如果能力小于怪兽，则必须贿赂
        if(ability < d[index]) {
            return p[index] + process1(d, p, index + 1, ability + d[index]);
        } else {
            // 如果能力大于当前怪兽，可以贿赂，可以不贿赂, 返回最小的钱数
            long p1 = process1(d, p, index + 1, ability);
            long p2 = p[index] + process1(d, p, index + 1, ability + d[index]);
            return Math.min(p1, p2);
        }
    }


    /**
     * 第一种方法的动态规划版本 （fun1）
     *
     */
    public long func2(int[] d, int[] p) {
        int sum = 0;
        for (int i = 0; i < p.length; i++) {
            sum += d[i];
        }
        int N = d.length;
        // dp[i][j] 表示通过i个怪兽，能力为j，花费的最小钱数
        long[][] dp = new long[N + 1][sum + 1];
        // 初始化dp
        for (int i = 0; i <= sum; i++) {
            dp[0][i] = 0;
        }

        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= sum; j++) {
                // 如果这种情况发生，必定是递归过程不会出现的状态，不比计算
                if(j + d[i] > sum) {
                    continue;
                }
                if(j < d[i]) {
                    // 如果当前能力小于怪兽能力, 花费的钱就等于搞定i+1怪兽的钱 加上搞定当前怪兽的钱
                    dp[i][j] = p[i] + dp[i + 1][j + d[i]];
                } else {
                    dp[i][j] = Math.min(dp[i + 1][j], p[i] + dp[i + 1][j + d[i]]);
                }
            }
        }
        return dp[0][0];
    }


    /**
     * 第二种暴力递归
     *   这种适用于 金钱的数值不超过10^8，那么执行效率较快
     *
     */
    public long func3(int[] d, int[] p) {
        // 计算总的钱数
        int allMoney = 0;
        for (int i = 0; i < p.length; i++) {
            allMoney += p[i];
        }

        int N = d.length;
        for (int i = 0; i < allMoney; i++) {
            // 因为钱是从小到大的，那么最先匹配的，钱数就是最少的
            // 如果返回的能力值 != -1，表示严格使用这么多钱，可以搞定N-1只怪兽
            if(process2(d, p, N - 1, i) != -1) {
                return i;
            }
        }
        // 如果都没有通过，那么返回所有的钱，所有的钱肯定可以通过
        return allMoney;
    }

    /**
     * 从0...index号怪兽，花的前，必须严格==money
     * 如果通过不了,返回-1
     * 如果可以通过，返回能通过情况下的最大能力值
     *
     */
    private long process2(int[] d, int[] p, int index, int money) {
        // 一只怪兽也没有遇到
        if(index == -1) {
            return money == 0 ? 0 : -1;
        }
        // index >= 0
        // 1. 如果不贿赂当前怪兽，那么index-1之前的所有怪兽花费的金钱必须等于money
        // 返回index-1的能力
        long preMaxAbility = process2(d,  p, index - 1, money);
        long p1 = -1;
        // 如果index-1只怪兽严格花费了money，那么能力不等于-1表示有方案，并且这个能力需要大于当前怪兽的能力
        if(preMaxAbility != -1 && preMaxAbility >= d[index]) {
            p1 = preMaxAbility;
        }

        // 2. 如果贿赂当前怪兽，那么判断index-1只怪兽是否可以严格花费money-p[index]的金钱
        //    减去当前的，index-1需要严格等于剩下的金钱
        long preMaxAbility2 = process2(d, p, index - 1, money - p[index]);
        long p2 = -1;
        if(preMaxAbility2 != -1) {
            // 不等于-1，表示有方案，那么能力值就等于加上当前的能力值
            p2 = d[index] + preMaxAbility2;
        }

        // 返回当前两种方案可以获取到的最大能力值
        return Math.max(p1, p2);
    }


    /**
     * 动态规划，  func3的动态规划版本
     *
     */
    public long func4(int[] d, int[] p) {
        int allMoney = 0;
        for (int i = 0; i < p.length; i++) {
            allMoney += p[i];
        }
        // dp[i][j]含义：
        // 能经过0～i的怪兽，且花钱为j（花钱的严格等于j）时的武力值最大是多少？
        // 如果dp[i][j]==-1，表示经过0～i的怪兽，花钱为j是无法通过的，或者之前的钱怎么组合也得不到正好为j的钱数
        int[][] dp = new int[d.length][allMoney + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j <= allMoney; j++) {
                dp[i][j] = -1;
            }
        }
        // 经过0～i的怪兽，花钱数一定为p[0]，达到武力值d[0]的地步。其他第0行的状态一律是无效的
        // 花钱数必须严格相等，所以0只怪兽的花钱数一定是p[0]
        dp[0][p[0]] = d[0];

        for (int i = 1; i < d.length; i++) {
            for (int j = 0; j <= allMoney; j++) {
                // 可能性一，为当前怪兽花钱
                // 存在条件：
                // j - p[i]要不越界，并且在钱数为j - p[i]时，要能通过0～i-1的怪兽，并且钱数组合是有效的。
                if(j >= p[i] && dp[i - 1][j - p[i]] != -1) {
                    dp[i][j] = dp[i - 1][j - p[i]] + d[i];
                }
                // 可能性二， 不为当前怪兽花钱
                // 存在条件：
                // 0~i范围上的怪兽可以凑齐j这么多钱，并且能力值可以通过当前怪兽
                if(dp[i - 1][j] >= d[i]) {
                    // i-1位置的最大能力值和可能性一的能力值取最大值
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                }
            }
        }
        int ans = 0;
        // dp表最后一行上的,dp[N-1][j]代表：
        // 能经过0～N-1的怪兽，且花钱为j（花钱的严格等于j）时的武力值最大是多少？
        // 那么最后一行上，最左侧的不为-1的列数(j)，就是答案（因为最左侧的j越小表示花前越少）
        for (int j = 0; j <= allMoney; j++) {
            if(dp[d.length - 1][j] != -1) {
                ans = j;
                break;
            }
        }

        return ans;
    }



    // for test
    public static int[][] generateTwoRandomArray(int len, int value) {
        int size = (int) (Math.random() * len) + 1;
        int[][] arrs = new int[2][size];
        for (int i = 0; i < size; i++) {
            arrs[0][i] = (int) (Math.random() * value) + 1;
            arrs[1][i] = (int) (Math.random() * value) + 1;
        }
        return arrs;
    }

}
