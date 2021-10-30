package com.github.左神算法.单调栈;

import org.junit.Test;

import java.util.Stack;

/**
 * @author tangsong
 * @date 2021/7/18 1:54
 */
public class Code03_LargestRectangleInHistogram {

    /**
     *
     * 给定一个非负数组arr，代表直方图, 每个位置的值代表这个直方图的高度有多高，
     * 例如 [1, 3, 2, 1]
     *      _
     *     | |
     *      _   _   _
     *     | | | | | |
     *  _   _   _   _
     * | | | | | | | |
     *
     * 表示每个位置的直方图有多高，分别是1,3,2,1的高度，计算里面包含的最大的长方形的面积，如：6
     *
     * 返回直方图的最大长方形面积
     *
     *
     * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
     */


    public int max(int[] arr) {
        // 从小到大的单调栈，如果遇到小于当前栈顶的数，那么需要将栈中元素弹出并计算，并且将当前的元素加入
        Stack<Integer> stack = new Stack<>();
        int maxArea = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            // 判断当前的元素是否能够加入到单调栈中
            // 这里将等于的元素也弹出进行处理，就算前一个元素计算错误，那么后面的相等的元素会计算正确
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int j = stack.pop();
                // 判断当前弹出的元素的左边比它小的第一个元素
                int k = stack.isEmpty() ? -1 : stack.peek();
                // 计算当前位置可以扩展的最大区域，就是向左第一个元素的下标和当前元素的下一个元素的下标组成的区域
                int curArea = (i - k - 1) * arr[j];
                maxArea = Math.max(curArea, maxArea);
            }
            stack.push(i);
        }
        // 判断栈中是否有元素，如果有元素，则将所有的元素弹出来进行结算
        while (!stack.isEmpty()) {
            int i = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (arr.length - k - 1) * arr[i];
            maxArea = Math.max(curArea, maxArea);
        }
        return maxArea;
    }


}
