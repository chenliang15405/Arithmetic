package com.arithmetic.features.arithmetic.interview;

import org.junit.Test;

/**
 * 矩阵旋转90度
 *
 * @author alan.chen
 * @date 2020/6/7 4:47 PM
 */
public class RotateMatrix {

    @Test
    public void test() {
        //int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};

        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;

        while (tR < dR) {
            // 每次交换一个层，逐渐向内圈交换
            ratote(matrix, tR++, tC++, dR--, dC--);
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

    }

    /**
     * 旋转90度的只能是正方形，长方形无法旋转，结构会改变
     *
     * 将矩阵4个j角的元素进行交换
     *
     *
     */
    public void ratote(int[][] matrix, int tR, int tC, int dR, int dC) {
        int times = dC - tC;
        int temp = 0;
        for (int i = 0; i < times; i++) {
            temp = matrix[tR][tC + i];
            matrix[tR][tC + i] = matrix[dR - i][tC];
            matrix[dR - i][tC] = matrix[dR][dC - i];
            matrix[dR][dC - i] = matrix[tR + i][dC];
            matrix[tR + i][dC] = temp;
        }
    }


}
