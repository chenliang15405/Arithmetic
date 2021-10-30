package com.github.bstation.array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangsong
 * @date 2021/6/6 17:07
 */
public class LongestOnes {

    /**
     * 给定一个只由0和1组成的二维数组matrix,每一行都可以保证所有的0在左侧，1在右侧，
     * 哪些行拥有最多的数量1，请放入一个列表中返回
     *
     *
     * 解法：
     *   1. 暴力解法，每一行从右到左遍历，记录每一列的长度，并返回列表
     *   2. 从右上到左下，依次比较每一行的长度
     *   3. 二分的方法找到每一行的最左边的1的位置，记录每一行的长度
     *   4. 二分的方法找到 0.。。上一行的位置 中1最左边的1的位置，记录每一行的长度
     *
     */

    @Test
    public void test() {
        int[][] matrix = {
                {0,1,1,1,1},
                {1,1,1,1,1},
                {0,0,0,1,1},
                {1,1,1,1,1},
                {0,0,0,0,0}
        };


        System.out.println(maxOne(matrix));

        System.out.println(maxOne1(matrix));

        System.out.println(maxOne2(matrix));
    }


    /**
     * 暴力解法
     *
     */
    public List<Integer> maxOne(int[][] matrix) {
        if(matrix == null || matrix.length <= 0) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        // 记录当前最长的1的行的个数
        int max = 0;

        for (int i = 0; i < matrix.length; i++) {
            int curMax = 0;
            for (int j = matrix[i].length - 1; j >= 0; j--) {
                if(matrix[i][j] == 1) {
                    curMax++;
                }
            }
            // 比较最大值
            if(curMax > max) {
                list.clear();
                list.add(i);
                max = curMax;
            } else if(curMax == max) {
                list.add(i);
            }
        }
        return list;
    }

    /**
     * 减少遍历每一行的次数
     *
     */
    public List<Integer> maxOne1(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        // 记录当前最长的1的行的个数
        int M = matrix[0].length;
        int max = 0;
        int cur = M;
        for (int i = 0; i < matrix.length; i++) {
            while (cur > 0 && matrix[i][cur - 1] == 1) {
                cur--;
            }
            // 得到当前最长得1的索引，计算长度和当前的最长的长度比较
            if(M - cur > max) {
                max = M - cur;
                list.clear();
                list.add(i);
            } else if(M - cur == max && matrix[i][cur] == 1) {
                // 如果相等的长度的行，则继续增加
                list.add(i);
            }
        }
        return list;
    }

    /**
     *  二分法
     */
    public List<Integer> maxOne2(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        int N = matrix.length;
        int M = matrix[0].length;
        int max = 0;
        for (int i = 0; i < N; i++) {
            int j = mostLeftOne(matrix[i], 0, M - 1);
            // 计算当前的长度
            if(M - j > max) {
                max = M - j;
                list.clear();
                list.add(i);
            } else if(M - j == max) {
                list.add(i);
            }
        }
        return list;
    }

    /**
     * 二分查找最左的一个数
     */
    private int mostLeftOne(int[] arr, int L, int R) {
        int ans = R + 1; // 记录当前最左边的1，如果没有一个1则返回当前行的长度，防止上面计算出1个1，计算长度时为0
        int mid = 0;
        while (L <= R) {
            mid = L + (R - L) / 2;
            if(arr[mid] == 1) {
                ans = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }

        }
        return ans;
    }

}
