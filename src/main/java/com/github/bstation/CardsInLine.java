package com.github.bstation;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/13 23:47
 */
public class CardsInLine {

    /**
     * 纸牌游戏，两个人分别取一列纸牌的左右两边，只能取两边，最后谁的分数最大
     *
     *  返回获胜者的最大分数
     */

    @Test
    public void test() {
        int[] arr = {4,7,9,5,19,29,80,4};

        System.out.println(win(arr));

        System.out.println(dpWay(arr));
    }


    public int win(int[] arr) {
        // 先手获取到的分数和后手获取到的分数，谁大谁就赢了
        return Math.max(f(arr, 0, arr.length-1), s(arr, 0, arr.length-1));
    }


    /**
     * 先手函数
     *   先手可以获取到的最大分数
     */
    private int f(int[] arr, int L, int R) {
        // 如果只有一张牌，则先手获取
        if(L == R) {
            return arr[L];
        }
        // 先手会选择两种选择中最大的一种方式，因为先手先选择，两种选择方式中会获取到最大的一种
        return Math.max(
                arr[L] + s(arr, L + 1, R),
                arr[R] + s(arr, L, R - 1)
        );
    }

    /**
     * 后手函数
     *   后手的话，只能获取到剩下分数最小的纸牌，因为大的已经被拿走了，肯定只会留下分数小的（总的分数小的，不是局部）
     *  在 l - i的区间范围作为后手可以获取到的最大分数（因为先手会自动选择最大的，所以作为后手只留下了最小的）
     */
    private int s(int[] arr, int l, int i) {
        // 当只有一张牌，因为是后手，所以返回0，已经被先手拿了
        if (l == i) {
            return 0;
        }
        // 后手只能获取到先手选择之后剩下的区间范围最小的分数
        // 区间范围-1，就表示先手已经选择过了，那么接下来就调用f函数获取最大的分数，但是先手选择之后，肯定会只留下最小的分数给后手，
        // 所以从最大的分数中获取到最小的（因为接下来作为先手，可以获取到两种选择中最大的，但是最后只能从里面选择一种较小的，因为先手是另一个玩家，会给你最小的选择可能）
            return Math.min(f(arr, l + 1, i), f(arr, l, i - 1));
    }


    /**
     * 暴力递归改造为动态规划
     */
    public int dpWay(int[] arr) {
        int N = arr.length;
        int[][] f = new int[N][N];
        int[][] s = new int[N][N];
        // 根据暴力递归的过程，f函数当L=R（即二维数组的对角线），s的对角线是0,初始化就是0了
        for (int i = 0; i < N; i++) {
            f[i][i] = arr[i];
        }

        for (int i = 1; i < N; i++) {
            int L = 0; // 行
            int R = i; // 列

            while (L < N && R < N) {
                // 从第一行的第1列开始填充
                f[L][R] = Math.max(
                        arr[L] + s[L + 1][R],
                        arr[R] + s[L][R - 1]
                );

                s[L][R] =  Math.min(f[L + 1][R], f[L][R - 1]);

                L++;
                R++;
            }
        }
        return Math.max(f[0][N-1], s[0][N-1]);
    }


}
