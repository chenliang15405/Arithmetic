package com.github.左神算法.斐波那契数列矩阵;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/7/26 1:14
 */
public class FibonacciProblem {

    /**
     * 斐波那契数列矩阵问题
     *   1. 递归，O(n)
     *   2. 矩阵问题： |Fn, Fn-1| = |Fn-1, Fn-2| * |a b|  = |F2,F1| * |a|^n-2
     *                                           |c d|
     *       可以通过前几项的数据，计算出递推公式，并且这是一个2阶可以计算的矩阵（n-2），然后通过前几项的数据计算出 a b c d矩阵的信息
     *       { 1, 1 },
     *       { 1, 0 }
     *   然后就可以通过公式计算第N个斐波那契数是多少， 计算|a|^n-2 的数据，然后和|F2, F1|进行计算即可
     *    注意：其中计算|a|^n-2可以使用二进制快速的计算出来
     *
     */
    @Test
    public void test() {
        int n = 19;
        System.out.println(fib1(n));
        System.out.println(fib2(n));
        System.out.println(fib3(n));
        System.out.println(fib4(n));
        System.out.println("===");

    }

    /**
     * 斐波那契数列 暴力递归
     *
     */
    public int fib1(int n) {
        if(n < 0) {
            return 0;
        }
        if(n == 1 || n == 2) {
            return 1;
        }
        return fib1(n - 1) + fib1(n - 2);
    }

    /**
     * 斐波那契数列 缓存优化
     *
     */
    public int fib2(int n) {
        int[] arr = new int[n + 1];
        Arrays.fill(arr, -1);
        
        return process(n, arr);
    }

    private int process(int n, int[] arr) {
        if(arr[n] != -1) {
            return arr[n];
        }
        if(n < 0) {
            arr[n] = 0;
            return 0;
        }
        if(n == 1 || n == 2) {
            arr[n] = 1;
            return 1;
        }
        int num = fib1(n - 1) + fib1(n - 2);
        arr[n] = num;
        return num;
    }
    
    /**
     * 斐波那契数列 动态规划
     *
     * 0 1 1 2 3 5
     *
     */
    public int fib3 (int n) {
        int a = 0;
        int b = 1;
        int c = 1;
        for (int i = 2; i < n; i++) {
            a = b;
            b = c;
            c = a + b;
        }
        return c;
    }
    
    /**
     * 斐波那契数列  矩阵快速计算技巧
     *
     *  |Fn, Fn-1| = |Fn-1, Fn-2| * |a|  = |F2,F1| * |a|^n-2
     *  |a| 是一个2x2的矩阵，因为斐波那契数列计算就是fn-2，所以就是2x2的矩阵
     *
     *  根据给定的前几项的数据，可以推导出来 |a| 矩阵的数据为 |1 1|
     *                                              |1 0|
     *
     *  那么最终的计算结果就是对矩阵计算为n-2的次方，然后和|1,1| * |a|  ^ n-2的结果
     *
     *  |1, 1| * |a b|  ，那么Fn = 1 * a + 1 * c
     *           |c d|
     *
     */
    public int fib4(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int[][] base = {
                {1, 1},
                {1, 0}
        };
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }

    /**
     * 矩阵的p次方的快速计算方法，可以通过设置一个单位矩阵，每次和自己相乘，
     * 然后根据p次方的二进制，判断当前的二进制是否为1，如果是1，那么就取这个单位矩阵作为当前的矩阵数
     *
     */
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
