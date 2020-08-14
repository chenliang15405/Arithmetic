package com.arithmetic.features.arithmetic.interview;

import org.junit.Test;

/**
 * 在行列都排好序的矩阵中找数
 *
 * 矩阵的每一行每一列都是排好序的，在这个矩阵中找到指定key是否存在
 *
 * @author alan.chen
 * @date 2020/6/7 6:27 PM
 */
public class MatrixSearchKey {


    @Test
    public void test() {
        int[][] matrix = new int[][] { { 0, 1, 2, 3, 4, 5, 6 },// 0
                { 10, 12, 13, 15, 16, 17, 18 },// 1
                { 23, 24, 25, 26, 27, 28, 29 },// 2
                { 44, 45, 46, 47, 48, 49, 50 },// 3
                { 65, 66, 67, 68, 69, 70, 71 },// 4
                { 96, 97, 98, 99, 100, 111, 122 },// 5
                { 166, 176, 186, 187, 190, 195, 200 },// 6
                { 233, 243, 321, 341, 356, 370, 380 } // 7
        };
        int key = 233;

        boolean exist = searchKey(matrix, key);

        System.out.println("是否存在：" + exist);
    }

    /**
     * 判断key是否在矩阵中
     *
     * @param matrix
     * @param key
     * @return
     */
    private boolean searchKey(int[][] matrix, int key) {
        int tR = 0;
        int tC = matrix[0].length - 1;

        // 循环判断是否key是否在矩阵中，从右上角开始寻找
        while (tR < matrix.length && tC >= 0) {
            // 如果相等，则直接返回
            if(matrix[tR][tC] == key) {
                return true;
            } else if(matrix[tR][tC] > key) {
                // 如果当前值大于key，因为行列都是排好序的，所以该列都大于key，列向前移动
                tC--;
            } else if(matrix[tR][tC] < key) {
                // 如果当前值小于key，因为行列都是排好序的，所以该行都小于key（当前点初始为右上角），行向下移动
                tR++;
            }
        }
        return false;
    }


}
