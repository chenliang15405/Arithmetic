package com.arithmetic.features.arithmetic.interview.monotonousstack;

import org.junit.Test;

import java.util.Stack;

/**
 * 单调栈问题
 *
 * 借助栈的结构解决问题，并且通常这个栈的顺序一般是从大到小或者从小到大的顺序排列
 *
 * @author tangsong
 * @date 2021/1/31 17:37
 */
public class Code03_LargestRectangleInHistogram {


    @Test
    public void testMaxRecSize() {
        int[][] map = {
                {1,0,1,1},
                {1,1,1,1},
                {1,1,1,0}
        };
        int maxArea = maxRecSize(map);
        System.out.println(maxArea);
    }


    /**
     * 求最大子矩阵的大小
     *
     * 给定一个整型矩阵，其中的值只有0或者1两种，求其中全是1的所有矩形区域中，最大的矩形区域为1的数量
     *
     * 例如：
     *  1 0 1 1
     *  1 1 1 1
     *  1 1 1 0
     *
     *  其中，最大的矩形区域有6个1，所以返回6
     *
     */
    public int maxRecSize(int[][] map) {
        if(map == null || map.length == 0 || map[0].length == 0) {
            return 0;
        }
        int maxArea = 0;
        // 计算最大区域的大小
        // 以每一行为底，依次递增，然后计算每次新形成的区域的最大为1的区域
        // map.length 表示行数，map[0].length表示列数
        int[] height = new int[map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // 如果当前的值为0，那么就是0，如果不为0，则数组中保存的数据为当前值+1，因为矩阵的所有数为1或者0
                height[j] = map[i][j] == 0 ? 0 : height[j] + 1;
            }
            int curArea = maxRecFromBottom(height);
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }


    /**
     * 计算给定数组的最大的区域
     *
     * [4, 2, 3, 5, 0] -> 4表示该列有4个连续的1, 0可能表示该列的当前行的数为0
     *
     * 计算这样一个数组中的最大的区域
     *
     */
    public int maxRecFromBottom(int[] height) {
        if(height == null || height.length <= 0) {
            return 0;
        }
        int maxArea = 0;
        // 从小到大排列的栈
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[stack.peek()] > height[i]) {
                // 获取到当前栈顶的元素（索引）
                Integer j = stack.pop();
                // 获取到前面的元素的索引（如果前面没有元素，那么就是-1，表示该元素的左边界）
                Integer k = stack.isEmpty() ? -1 : stack.peek();
                // 计算左边界到有边界的所有的区域的大小
                int curArea = (i - k - 1) * height[j];
                maxArea = Math.max(curArea, maxArea);
            }
            stack.push(i);
        }

        // 当循环结束，stack中还有元素，那么需要计算这些元素的区域
        while (!stack.isEmpty()) {
            Integer j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (height.length - k - 1) * height[j];
            maxArea = Math.max(curArea, maxArea);
        }
        return maxArea;
    }

}
