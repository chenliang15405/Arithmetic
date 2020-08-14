package com.arithmetic.features.arithmetic.interview.recursion;

import org.junit.Test;

/**
 * 一个二维数组，二维数组中每个数都是正数，要求从左上角走到右下角，每一步只能向右或者向下，
 * 走过的路径的所有数字都要累加，返回最小的路径和
 *
 * @author alan.chen
 * @date 2020/7/26 11:27 AM
 */
public class MatrixMinPath {


    @Test
    public void test() {
        int[][] matrix = new int[][]{{1, 2, 3, 4}, {3, 1, 2, 3}, {1, 3, 2, 1}, {3, 1, 2, 3}};
        int R = matrix.length - 1;
        int C = matrix[0].length - 1;
        int minPath = minPath(matrix, 0, 0, R, C);

        System.out.println(minPath);
    }

    private int minPath1(int[][] matrix, int x, int y, int R, int C) {
        if(x == R && y == C) {
            return matrix[x][y];
        }
        if(x == R && y < C) {
            return matrix[x][y] + minPath1(matrix, x, y + 1, R, C);
        }
        if(x < R && y == C) {
            return matrix[x][y] + minPath1(matrix, x + 1, y, R, C);
        }
        int path1 = minPath1(matrix, x + 1, y, R, C);
        int path2 = minPath1(matrix, x, y + 1, R, C);

        return matrix[x][y] + Math.min(path1, path2);
    }


    private int minPath(int[][] matrix, int x, int y, int R, int C) {
        // 当到达目标点，则直接返回该点的数据
        if (x == R && y == C) {
            return matrix[x][y];
        }
        // 当移动到最下层时，当前点+向右递归的所有数据
        if (x == R && y < C) {
            return matrix[x][y] + minPath(matrix, x, y + 1, R, C);
        }
        // 当移动过到最右边时，当前点+向下递归的所有数据
        if (x < R && y == C) {
            return matrix[x][y] + minPath(matrix, x + 1, y, R, C);
        }
        // 向下走
        int path1 = minPath(matrix, x + 1, y, R, C);
        // 向右走
        int path2 = minPath(matrix, x, y + 1, R, C);

        return matrix[x][y] + Math.min(path1, path2);
    }


}
