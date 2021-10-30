package com.github.bstation.array;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/6/6 17:45
 */
public class SetMatrixZeros {

    /**
     * 矩阵置0
     *
     *  给定一个二维数组matrix，所有数都是整数，请你把其中所有的0做上下左右方向的延伸，返回处理之后的matrix
     *  比如，注意其中有两个0，那么每个0都向上下左右延伸
     *  7 0 1 4 6
     *  6 5 3 2 1
     *  4 2 1 0 3
     *  8 2 1 2 9
     * 应该处理成
     * 0 0 0 0 0
     * 6 0 3 0 1
     * 0 0 0 0 0
     * 8 0 1 0 9
     *
     * 要求：整个处理过程都直接在matrix上发生，除此之外额外空间复杂度请做到O(1)，也就是只用有限的几个变量
     *
     *
     * 解法：
     *   1. 使用额外空间，一个列表记录 某一行是否有0， 另一个列表记录某一列是否有0，最终再次遍历，并将指定的位置置0
     *   2. 不适用额外空间，使用两个变量，记录0行是否有0,0列是否有0，相当于将0行和0列当做额外空间使用
     *
     */

    @Test
    public void test() {
        int[][] matrix = {
                {7,0,1,4,6},
                {6,5,3,2,1},
                {4,2,1,0,3},
                {8,2,1,2,9}
        };
        int[][] matrix2 = {
                {7,0,1,4,6},
                {6,5,3,2,1},
                {4,2,1,0,3},
                {8,2,1,2,9}
        };

        setZero(matrix);
        setZero2(matrix2);

        printMatrix(matrix);
        System.out.println("---------------------------------");
        printMatrix(matrix2);
    }


    /**
     * 不适用额外空间，将矩阵中指定的数据设置为0
     *
     */
    public void setZero(int[][] matrix) {
        if(matrix == null || matrix.length <= 0) {
            return;
        }
        // 使用两个变量记录第一行是否有0以及第一列是否有0
        boolean isRow0 = false;
        boolean isCol0 = false;

        int N = matrix.length;
        int M = matrix[0].length;
        // 判断第一行是否有0
        for (int i = 0; i < M; i++) {
            if(matrix[0][i] == 0) {
                isRow0 = true;
                break;
            }
        }
        // 判断第一列是否有0
        for (int j = 0; j < N; j++) {
            if(matrix[j][0] == 0) {
                isCol0 = true;
                break;
            }
        }

        // 从1,1 开始遍历，如果有0则将该位置对应的第一行和第一列对应的坐标点设置为0
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                if(matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // 将第一行和第一列中为0的点的对应的列设置为0
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                // 判断当前行、当前列的第一行和第一列是否为0，如果有0则将当前的坐标置为0
                if(matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // 判断是否需要将量第一行和第一列设置为0
        if(isRow0) {
            for (int i = 0; i < M; i++) {
                matrix[0][i] = 0;
            }
        }
        if(isCol0) {
            for (int i = 0; i < N; i++) {
                matrix[i][0] = 0;
            }
        }
    }


    /**
     * 矩阵置0，使用占位数组的方式
     *
     */
    public void setZero2(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        boolean[] row = new boolean[n];
        boolean[] col = new boolean[m];

        // 设置数组的占位标识，将为0的位置的行和列的数组对应的位置标记
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(matrix[i][j] == 0) {
                    row[i] = col[j] = true;
                }
            }
        }

        // 遍历矩阵，将标记的行和列全部设置为0
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(row[i] || col[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }




    private void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + ",");
            }
            System.out.println();
        }

    }
}
