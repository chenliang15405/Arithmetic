package com.github.左神算法.动态规划;

import com.github.bstation.tree.Node;
import org.junit.Test;


/**
 * @author tangsong
 * @date 2021/7/4 14:52
 */
public class SplitSumClosedSizeHalf {

    /**
     * 给定一个正数数组arr，请把arr中所有的数分成两个集合
     * 如果arr长度为偶数，两个集合包含数的个数要一样多
     * 如果arr长度为奇数，两个集合包含数的个数必须只差一个
     * 请尽量让两个集合的累加和接近
     * 返回：
     *    最接近的情况下，较小集合的累加和
     *
     */
    @Test
    public void test() {
        int[] arr = {1, 48, 40, 32, 44, 38};
        System.out.println(right(arr));
        System.out.println(dp(arr));
    }

    /**
     * 暴力递归
     *
     */
    public int right(int[] arr) {
        // 对arr求和
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        int n = arr.length;
        // 判断arr的长度是偶数还是奇数
        if((n & 1) == 0) {
            // 偶数
            return process(arr, n / 2, sum / 2, 0);
        } else {
            // 奇数, 那么两个个数会差1, 返回较小的那个集合的累加和
            int s1 = process(arr, n / 2, sum / 2, 0);
            int s2 = process(arr, n / 2 + 1, sum / 2, 0);
            return Math.min(s1, s2);
        }
    }

    // 从index开始选择 arr[index...] 总共选择picks个数，总的累加和需要小于等于sum
    private int process(int[] arr, int picks, int sum, int index) {
        if(sum < 0) {
            // 使用 -1 标识之前的选择方式是无效的
            return -1;
        }
        if(index == arr.length) {
//            if(picks == 0) {
//                // 这个sum >= 0 总是为true，因为上面对sum进行了判断
//                return sum >= 0 ? 0 : -1;
//            } else {
//                return -1;
//            }
            // 所以只需要判断index到达最后一个，picks是否等于0
            return picks == 0 ? 0 : -1; // 使用 -1 标识无效之前的选择方式是无效的
        }
        // 选择不要当前数
        int p1 = process(arr, picks, sum, index + 1);
        // 要当前数
        int p2 = -1;
        int next = process(arr, picks - 1, sum - arr[index], index + 1);
        if(next != -1) {
            p2 = arr[index] + next;
        }
        // 在两种方式选择的累加和中，肯定都是小于等于sum，那么选择其中一个较大的
        return Math.max(p1, p2);
    }


    /**
     * 动态规划
     *
     */
    public int dp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum /= 2;
        int N = arr.length;
        int M = (N + 1) / 2;
        int[][][] dp = new int[N + 1][M + 1][sum + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        for (int rest = 0; rest <= sum; rest++) {
            dp[N][0][rest] = 0;
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int picks = 0; picks <= M; picks++) {
                for (int rest = 0; rest <= sum; rest++) {
                    int p1 = dp[i + 1][picks][rest];
                    // 就是要使用arr[i]这个数
                    int p2 = -1;
                    int next = -1;
                    if (picks - 1 >= 0 && arr[i] <= rest) {
                        next = dp[i + 1][picks - 1][rest - arr[i]];
                    }
                    if (next != -1) {
                        p2 = arr[i] + next;
                    }
                    dp[i][picks][rest] = Math.max(p1, p2);
                }
            }
        }
        if ((arr.length & 1) == 0) {
            return dp[0][arr.length / 2][sum];
        } else {
            return Math.max(dp[0][arr.length / 2][sum], dp[0][(arr.length / 2) + 1][sum]);
        }
    }


}
