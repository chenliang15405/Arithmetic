package com.github.bstation.array;

/**
 * @author tangsong
 * @date 2021/5/30 16:59
 */
public class SubMatrixMaxSum {

    /**
     * 给定一个矩阵，求最大子矩阵的累加和
     */


    /**
     * 解题思路： 枚举所有可能，第1行，第2行，第3行。。。 ，然后第1行+第2行，第1行+第2+第3行... 第2行+第3行...
     *  每行采用压缩的形式将数字进行计算，那么就获取到一个一维数组，然后计算这个一维数组中的最大累加和，这个最大累加和就对应了
     *  矩阵中的一个子矩阵。
     *
     *
     *
     */
    public int getMaxSum(int[][] matrix) {
        if(matrix == null || matrix.length == 0) {
            return -1;
        }
        int N = matrix.length;

        int max = Integer.MIN_VALUE;
        // 对矩阵的多行进行计算
        for (int i = 0; i < N; i++) { // 开始的行号 i
            // 使用数组装每行的值（以及压缩多行之后的值，放入到一个数组中）
            int[] arr = new int[matrix[i].length];
            for (int j = i; j < N; j++) { // 结束的行号 j ，每次计算相邻的行
                // 遍历数组，因为数组保存的是矩阵的列
                int cur = 0; // 每一行都有一个当前值用来记录当前的最大值，第一行需要计算累加和的最大值，然后第一行和第二行合并之后还需要继续计算一个新的最大的累加和
                for (int k = 0; k < arr.length; k++) {
                    // 将多行的值放入数组中
                    arr[k] += matrix[j][k]; // 将多行的值进行累加，相同的列累加到一个索引的位置上
                    cur += arr[k]; // 将当前索引的值相加到cur上，相当于一维数组求最大累加和
                    max = Math.max(max, arr[k]); // 计算最大值
                    // 判断当前的累加和是否<0,如果小于0则需要重置，因为连续的累加和不可能加上负数才被计算出来最大的累加和
                    cur = cur > 0 ? cur : 0; // 因为arr[k]不能直接重置为0，所以需要cur变量来代替
                }
            }
        }
        return max;
    }

}
