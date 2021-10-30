package com.github.左神算法.斐波那契数列矩阵;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/7/26 1:19
 */
public class CowProblem {

    /**
     * 斐波那契数列矩阵问题
     *
     *   第一年农场有1只成熟的母牛A, 往后的每年：
     *   1> 每一种成熟的母牛都会生一只母牛
     *   2> 每一只新出生的母牛都会在出生的第三年成熟
     *   3> 每一只母牛永远不会死
     *
     *  返回N年后牛的数量
     *
     *  Fn = Fn-1 + Fn-3
     *  第一年：1
     *  第二年：2
     *  第三年：3
     *  |Fn, Fn-1, Fn-2| = |Fn-1, Fn-2, Fn-3| * |a|^n-3 = |F3, F2, F1| * |a|^n-3 = |3, 2, 1| * |a| ^ n-3,
     *  可以通过前几项的递归，计算求得|a|的矩阵的数据
     *
     */
    @Test
    public void test() {
        int n = 19;
        System.out.println(c1(n));
        System.out.println(c2(n));
        System.out.println(c3(n));

    }

    /**
     * 暴力递归
     *   n 表示第几年
     *   每年的牛 = 去年的牛 + 三年前的牛
     *
     * 优化：加缓存
     */
    public int c1(int n) {
        if(n <= 0) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        // 去年的牛数量 + 三年前牛的数量
        return c1(n - 1) + c1(n - 3);
    }

    /**
     * 动态规划
     *
     */
    public int c2(int n) {
        int a = 0;
        int b = 1;
        int c = 2;
        int d = 3;

        for (int i = 4; i <= n; i++) {
            a = b;
            b = c;
            c = d;
            d = c + a;
        }
        return d;
    }


    /**
     * 斐波那契数列矩阵计算技巧套路
     *
     *  矩阵快速幂算法
     */
    public int c3(int n) {
        if(n <= 0) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        // 根据前几项的计算，可以计算|a|矩阵的具体数据
        int[][] base = {
                { 1, 1, 0 },
                { 0, 0, 1 },
                { 1, 0, 0 } };
        int[][] res = matrixPower(base, n - 3);
        // 因为根据递推 |Fn, Fn-1, Fn-2| = |F3, F2, F1| * |a|^n-3 = |3, 2, 1| * |a| ^ n-3
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
    }

    private int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        // 初始化一个单位矩阵，根据单位矩阵计算，实现快速计算矩阵的p次方
        for (int i = 0; i < m.length; i++) {
            res[i][i] = 1;
        }
        // 矩阵的n次方计算最快的方式，让一个初始的矩阵每次自己乘以自己，然后通过二进制，在当前需要的时候，乘以该自己乘自己的矩阵即可
        // res = 矩阵中的1
        int[][] t = m;
        // 10 >> 1 向右移1位，10的二进制为 00001010 移1位之后，00000101，整体向右移动1位，然后高位补0，即除以2，因为都是2的次方，然后向右移动一位，就是整体2的次方-1
        for (; p != 0; p >>= 1) {
            if((p & 1) != 0) {
                // 如果二进制的位=1，当前的矩阵 乘以 自己乘以自己的矩阵
                res = multiMatrix(res, t);
            }
            t = multiMatrix(t, t);
        }
        return res;
    }

    // 两个矩阵乘完之后的结果返回
    private int[][] multiMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }

}
