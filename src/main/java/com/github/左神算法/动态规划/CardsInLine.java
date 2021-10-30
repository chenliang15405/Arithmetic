package com.github.左神算法.动态规划;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/6/21 23:40
 */
public class CardsInLine {

    /**
     * 纸牌游戏，两个人分别取一列纸牌的左右两边，只能取两边，最后谁的分数最大
     *
     *  返回获胜者的最大分数
     */

    @Test
    public void test() {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(way1(arr));
        System.out.println(way2(arr));
        System.out.println(way3(arr));

    }

    int n = 00000000000000000000000000001011;

    @Test
    public void hammingWeight() {
        // 因为是32位的二进制串，那么使用每一个位上的数字和当前的n进行&运算，如果是1，则当前位置就是1，如果不是1表示当前位置不是1
        int count = 0;
        for(int i = 0; i < 32; i++) {
            int j = (1 << i);
            int k = j&n;
            if(k != 0) {
                count++;
            }
        }
    }

    /**
     * 暴力递归
     *
     */
    public int way1(int[] arr) {
        // 最大分数，就是在先手和后手中取一个最大的
        return Math.max(f1(arr, 0, arr.length - 1), g1(arr, 0, arr.length - 1));
    }

    /**
     * 先手操作的函数
     *
     */
    public int f1(int[] arr, int L, int R) {
        if(L == R) {
            return arr[L];
        }
        int x = arr[L] + g1(arr, L + 1, R);
        int y = arr[R] + g1(arr, L, R - 1);

        return Math.max(x, y);
    }

    /**
     * 后手函数
     */
    private int g1(int[] arr, int L, int R) {
        if(L == R) {
            // 后手的话，如果只有一个数，那么肯定被先手拿走了
            return 0;
        }
        // 拿到先手获取之后最小的一个
        return Math.min(f1(arr, L, R - 1), f1(arr, L + 1, R));
    }


    /**
     * 记忆化搜索
     */
    public int way2(int[] arr) {
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        // 先填充为-1，不影响决策
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fmap[i][j] = -1;
                gmap[i][j] = -1;
            }
        }
        // 最大分数，就是在先手和后手中取一个最大的
        return Math.max(f2(arr, 0, arr.length - 1, fmap, gmap), g2(arr, 0, arr.length - 1, fmap, gmap));
    }



    private int f2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        // 从缓存中取
        if(fmap[L][R] != -1) {
            return fmap[L][R];
        }
        int ans = 0;
        if(L == R) {
            ans = arr[L];
        } else {
            int left = arr[L] + g2(arr, L + 1, R, fmap, gmap);
            int right = arr[R] + g2(arr, L, R - 1, fmap, gmap);
            ans = Math.max(left, right);
        }
        fmap[L][R] = ans;
        return ans;
    }

    private int g2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if(gmap[L][R] != -1) {
            return gmap[L][R];
        }
        int ans = 0;
        if(L == R) {
            // 后手的话，如果只有一个数，那么肯定被先手拿走了
            ans = 0;
        } else {
            ans = Math.min(f2(arr, L, R - 1, fmap, gmap), f2(arr, L + 1, R, fmap, gmap));
        }
        gmap[L][R] = ans;
        return ans;
    }


    /**
     * 动态规划
     *   使用两个dp数组，并且两个dp数组的数据相互依赖
     *
     */
    public int way3(int[] arr) {
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];

        // 初始化dp数组
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(i == j) {
                    fmap[i][j] = arr[i];
                }
            }
        }
        // gmap[i][i] = 0 不用初始化

        // 分析递归的过程可以知道两个数组是相互依赖斜线上的左边数据，所以需要同步增加，因为对角线已经填充好了
        for (int i = 1; i < N; i++) {
            int L = 0;
            int R = i;
            while (R < N) {
                // 只填充对角线，每次填充的行都是递减的，所以R需要初始值是递增的
                fmap[L][R] = Math.max(arr[L] + gmap[L + 1][R], arr[R] + gmap[L][R - 1]);
                gmap[L][R] = Math.min(fmap[L][R - 1], fmap[L + 1][R]);
                L++;
                R++;
            }
        }

        return Math.max(fmap[0][N-1], gmap[0][N-1]);
    }


}
