package com.arithmetic.features.arithmetic.interview;

import org.junit.Test;

/**
 *
 * 将矩阵从(0,0)开始按照"之"字打印
 *
 * 思路：定义4个位置，表示横向 纵向的坐标，定义一个boolean变量表示从上向下打印或者从下向上打印
 * 打印一次之后将横坐标的位置和纵坐标的位置移动到下一个，判断是否到最右边和最下边，如果到了，则开始移动纵向的坐标和横向的坐标
 * 每次移动之后则打开打印，总体循环的次数是横向坐标的行达到最后一行
 *
 *
 * @author alan.chen
 * @date 2020/6/7 6:18 PM
 */
public class ZigZagMatrixPrint {


    @Test
    public void test() {
        //int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        int tR = 0;
        int tC = 0;
        int dR = 0;
        int dC = 0;

        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;

        boolean fromUp = false;

        while (tR < endR + 1) {
            printMatrix(matrix, tR, tC, dR, dC, fromUp);
            tR = tC == endC ? tR + 1 : tR;
            tC = tC == endC ? tC : tC + 1;
            dC = dR == endR ? dC + 1 : dC;
            dR = dR == endR ? dR : dR + 1;
            fromUp = !fromUp;
        }

    }

    @Test
    public void test2() {
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};

        int tR = 0;
        int dR = 0;
        int tC = 0;
        int dC = 0;

        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;

        boolean fromUp = false;

        while (tR < endR + 1) {
            printLine(matrix, tR, tC, dR ,dC, fromUp);

            tR = tC == endC ? tR + 1: tR;
            tC = tC == endC ? tC : tC + 1;
            dC = dR == endR ? dC + 1 : dC;
            dR = dR == endR ? dR : dR + 1;
            fromUp = !fromUp;
        }


    }

    private void printLine(int[][] matrix, int tR, int tC, int dR, int dC, boolean fromUp) {
        if(fromUp) {
            while(tR <= dR) {
                System.out.println(matrix[tR++][tC--] + " ");
            }
        } else {
            while(dR >= tR) {
                System.out.println(matrix[dR--][dC++] + " ");
            }
        }

    }


    private void printMatrix(int[][] matrix, int tR, int tC, int dR, int dC, boolean fromUp) {
        if(fromUp) {
            // 从上向下
            while (tR <= dR) {
                System.out.println(matrix[tR++][tC--] + " ");
            }
        } else {
            // 从下向上
            while (dR >= tR) {
                System.out.println(matrix[dR--][dC++] + " ");
            }
        }
    }


}
