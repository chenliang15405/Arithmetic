package com.arithmetic.features.arithmetic.interview.dp;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alan.chen
 * @date 2020/6/27 4:07 PM
 */
public class MinPath {

    @Test
    public void test() {
        int[][] matrix = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};

        boolean[] visited = new boolean[matrix.length * matrix[0].length];

        minPath(matrix, visited, 0, 0, matrix.length - 1, matrix[0].length - 1);

    }

    private int minPath = Integer.MAX_VALUE;
    private int path;
    private List<Point> list = new ArrayList<>();

    public void minPath(int[][] matrix, boolean[] visited, int i, int j, int R, int C) {
        if(i == matrix.length - 1 && j == matrix[0].length - 1) {
            minPath = Math.min(minPath, path);
            path = 0;
            return;
        }
        if(i + 1 <= R) {
            list.add(new Point(i + 1, j));
        }
        if(j + 1 <= C) {
            list.add(new Point(i, j + 1));
        }

        while(!list.isEmpty()) {
            Point remove = list.remove(0);
            path += matrix[remove.x][remove.y];
            minPath(matrix, visited, remove.x, remove.y, R, C);
        }

        System.out.println("最小路径" + minPath);
    }


}
