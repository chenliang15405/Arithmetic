package com.arithmetic.features.arithmetic.interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 转圈打印矩阵
 *
 * @author alan.chen
 * @date 2020/6/7 4:46 PM
 */
public class PrintMatrixSpiralOrder {

    @Test
    public void test() {
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;

        List<Integer> list = new ArrayList<>();

        while (tR <= dR && tC <= dC) {
            printMatrix(matrix, tR++, tC++, dR--, dC--, list);
        }

        list.forEach(System.out::println);
    }

    private List<Integer> printMatrix(int[][] matrix, int tR, int tC, int dR, int dC, List<Integer> list) {
        int a = tR;
        int b = tC;
        int c = dR;
        int d = dC;

        if(a == c) {
            // 如果只有中间一行数据了
            while (b <= d) {
                list.add(matrix[tR][b++]);
            }
        } else if (b == d) {
            // 如果只有中间一列数据
            while (a <= c) {
                list.add(matrix[a++][tC]);
            }
        } else {
            // 向右、向下、向左、向上打印每一圈
            while (b < dC) {
                list.add(matrix[tR][b++]);
            }
            while (a < dR) {
                list.add(matrix[a++][dC]);
            }
            while (d > tC) {
                list.add(matrix[dR][d--]);
            }
            while (c > tR) {
                list.add(matrix[c--][tC]);
            }
        }
        return list;
    }
}
