package com.arithmetic.features.datastructure;

import org.junit.Test;


/**
 * 八皇后问题  回溯递归算法
 *
 * 字符串1是否包含字符串2，包含则返回位置，不包含返回-1
 *
 *
 * @author alan.chen
 * @date 2020/5/27 10:02 PM
 */
public class Queue8 {


    int count = 0;

    @Test
    public void test1() {
        // arr的索引表示第几个皇后，索引对应的数据表示该皇后所在的位置
        int[] arr = new int[8];

        arrange(arr, 0);

        System.out.println("所有的解法： " + count); // 92
    }

    @Test
    public void test2() {
        int[] arr = new int[8];

        arrange2(arr, 0);

        System.out.println("所有的解法： " + count); // 92
    }


    public void arrange2(int[] arr, int index) {
        if(arr.length == index) {
            count++;
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
            return;
        }

        for (int i = 0; i < 8; i++) {
            arr[index] = i;
            if(judge2(arr, index)) {
                arrange2(arr, index + 1);
            }
        }
    }

    private boolean judge2(int[] arr, int index) {
        for (int i = 0; i < index; i++) {
            if(arr[i] == arr[index] || Math.abs(index - i) == Math.abs(arr[index] - arr[i])) {
                return false;
            }
        }
        return true;
    }


    /**
     * 使用递归回溯算法算出所有的解法，每次重新进入到arrange方法则都会重新开始for循环，所以会有回溯
     *
     *
     * @param arr 排列皇后位置的数组
     * @param n 第几个皇后
     */
    public void arrange(int[] arr, int n) {
        // 当排列到第8个皇后的时候, arr就长度就是有多少个皇后排列
        if(n == arr.length) {
            // 当第一种解法完成之后，会reutrn，然后最后一个皇后排列所有的位置之后，没有找到新的解法，就会回溯，
            // 逐渐向上回溯，又开始向下递归，寻找新解法

            //打印当前解法
            print(arr);
            return;
        }

        // 将当前的第n个皇后放在第i个位置，8表示该列的长度是8就是有8个位置可以放
        for (int i = 0; i < 8; i++) {
            arr[n] = i;

            // 判断当前位置是否可以放
            if(judge(arr, n)) {
                // 当 当前皇后和之前的皇后位置不冲突的时候，确定下一个皇后的位置
                arrange(arr, n + 1);
            }
            // 如果当前皇后的位置总是和之前的皇后位置冲突，则8个位置排列之后还是冲突，则回溯到上一个皇后重新排列之后，再递归到
            // 下个皇后重新开始排列
        }
    }

    /**
     * 判断当前位置的皇后和之前的所有的皇后是否位置冲突
     *
     * arr[i] == arr[n] 判断是否在同一列
     * Math.abs(n-i) == Math.abs(arr[n] - arr[i] 判断是否在同一斜线
     */
    private boolean judge(int[] arr, int n) {
        // 比较当前皇后和之前的每一个皇后是否在同一列，同一斜线，同一行（因为程序控制是不同的行）
        for (int i = 0; i < n; i++) {
            if(arr[i] == arr[n] || Math.abs(n-i) == Math.abs(arr[n] - arr[i])) {
                return false;
            }
        }
        return true;
    }


    private void print(int[] arr) {
        count++;
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
