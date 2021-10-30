package com.github.左神算法.斐波那契数列矩阵;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/8/7 16:11
 */
public class ZeroLeftOneStringNumber {

    /**
     * 给定一个数N, 想象只由0和1两种字符，组成的所有长度为N的字符串，如果某个字符串，任何0字符的左边都有1
     * 紧挨着，认为这个字符串达标，返回有多少个达标的字符串
     *
     *
     *  只由当第一个数是1的时候，后续的字符串才可能会达标，所以让第一个数为1，那么第二个格子有2中选择:
     *  1> 当第二个格子是1，那么达标的字符串就是f(n-1)
     *  2> 当第二个格子是0，那么达标的字符串就是f(n-2)
     *
     *  f(i) 表示i位置的字符串是1的时候，达标的字符串个数
     *
     *  F(n) = F(n-1) + F(n-2)
     *
     *  就是斐波那契数列，可以直接矩阵计算法计算
     *
     */
    @Test
    public void test() {
        for (int i = 0; i != 20; i++) {
            System.out.println(getNum0(i));
            System.out.println(getNum1(i));
            System.out.println(getNum2(i));
            System.out.println(getNum3(i));
            System.out.println("===================");
        }
    }

    public static int getNum0(int n) {
        if (n < 1) {
            return 0;
        }
        if(n == 1 || n ==2) {
            return n;
        }
        return getNum0(n-1) + getNum0(n-2);
    }

    public static int getNum1(int n) {
        if (n < 1) {
            return 0;
        }
        return process(1, n);
    }

    public static int process(int i, int n) {
        if (i == n - 1) {
            return 2;
        }
        if (i == n) {
            return 1;
        }
        return process(i + 1, n) + process(i + 2, n);
    }

    public static int getNum2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int pre = 1;
        int cur = 1;
        int tmp = 0;
        for (int i = 2; i < n + 1; i++) {
            tmp = cur;
            cur += pre;
            pre = tmp;
        }
        return cur;
    }

    /**
     * 矩阵快速幂算法
     *
     */
    public static int getNum3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = { { 1, 1 }, { 1, 0 } };
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }


    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] tmp = m;
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = muliMatrix(res, tmp);
            }
            tmp = muliMatrix(tmp, tmp);
        }
        return res;
    }

    public static int[][] muliMatrix(int[][] m1, int[][] m2) {
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
