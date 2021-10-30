package com.github.左神算法.单调栈;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author tangsong
 * @date 2021/7/19 1:23
 */
public class Code04_MaximalRectangle {

    /**
     * 给定一个二维数组matrix，其中的值不是0就是1，返回全部由1组成的最大子矩阵，内部有多少个1
     * <p>
     * 思路：
     * 1. 压缩矩阵放，使用一维数组，统计二维数组中，以每一行为底组成的最大的矩阵的1的数量
     * 2. 相当于使用一维数组，将二维数组中的子矩阵转换为了直方图，然后就是计算给定直方图中的最大长方形的面积 -- {@link Code03_LargestRectangleInHistogram}
     * <p>
     * https://leetcode.com/problems/maximal-rectangle/
     */

    /**
     * 二维数组转换为一维数组，并使用单调栈
     *
     */
    public int maximalRectangle(char[][] map) {
        // 创建一维数组，表示以每一行为底的数组并且其中1的长度（1的数值不能中断）
        int[] bottom = new int[map[0].length];
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 如果当前为底的行某一列是0，那么这一个元素对应的就是0，否则就是上一行的这个列的元素+1个长度
                bottom[j] = map[i][j] == '0' ? 0 : bottom[j] + 1;
            }
            // 这样组成的一维数组组成的数组表示一个直方图，所以只需要计算直方图的最大面积就是表示最大子矩阵
            int curArea = maxRecFromBottom(bottom);
            // 对每一行为底时组成的矩阵（直方图）都计算最大的子矩阵（因为元素都是0或者1，所以最大的面积就是最多的1的子矩阵）
            max = Math.max(max, curArea);
        }
        return max;
    }

    /**
     * 单调栈计算直方图的最大面积
     */
    private int maxRecFromBottom(int[] bottom) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;

        for (int i = 0; i < bottom.length; i++) {
            while (!stack.isEmpty() && bottom[stack.peek()] > bottom[i]) {
                Integer pop = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek();
                // 计算当前的最大面积
                int curArea = (i - left - 1) * bottom[pop];
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            int left = stack.isEmpty() ? -1 : stack.peek();
            // 计算当前的最大面积，因为已经将所有的元素遍历完成当前元素都没有弹出，那么表示当前元素的右边到数组的最右边都没有比当前元素小的元素
            // 所以使用bottom.length计算
            int curArea = (bottom.length - left - 1) * bottom[pop];
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }
}
