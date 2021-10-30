package com.github.左神算法.单调栈;

import org.junit.Test;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author tangsong
 * @date 2021/7/18 1:42
 */
public class Code02_AllTimesMinToMax {

    /**
     * 给定一个只包含正数的数组arr，arr中任何一个子数组sub, 一定都可以算出(sub累加和)*（sub中的最小值）是什么，
     * 那么所有的子数组中，这个值最大是多少？
     *
     *  思路：
     *    1. 每次都以某一个元素作为最小值进行计算，那么最大的值一定是累加和最大的
     *    2. 因为是正数数组，最大的累加和一定是范围最大的，那么计算以每一个元素为最小值时，左边最大能扩到多少，右边最大能扩到多少
     *       这里的能扩到多少是指，扩到两边都小于等于当前数的那个位置的前一个数
     *    3. 等于的位置也算不能扩的位置，因为如果相等的话，这个位置计算错了，那么下一个位置时可以计算对的，
     *       因为单调栈，上一个相同元素位置左边的范围和当前元素的左边范围应该是一致的，但是当前元素右边还可以再扩展，当前元素肯定比上一个元素的值大
     *    4. 使用单调栈就可以解决，因为每次都记录当前元素为最小值，可以组成的最大范围的子数组，然后计算sum值，这样遍历数组之后，就可以得到最大值
     *
     */
    @Test
    public void test() {
        int testTimes = 2000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = gerenareRondomArray();
            if (max(arr) != maxWithStack(arr)) {
                System.out.println("FUCK!");
                break;
            }
        }
        System.out.println("test finish");
    }


    /**
     * 暴力法
     *
     */
    public int max(int[] arr) {
        int max = Integer.MIN_VALUE;
        // 暴力每个子数组，并计算其子数组的值
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                // 对子数组遍历，并计算最小值和累加和
                int min = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    min = Math.min(min, arr[k]);
                    sum += arr[k];
                }
                max = Math.max(max, sum * min);
            }
        }
        return max;
    }


    /**
     * 单调栈
     *   1. 以每个元素为最小值时，组成的子数组，计算其生成的值
     *   2. 对一个[i...j]的子数组计算其累加和，可以使用前缀和的方式，O(1)计算累加和
     *
     */
    public int maxWithStack(int[] arr) {
        // 初始化一个累加和数组
        int N = arr.length;
        int[] sum = new int[N];
        sum[0] = arr[0];
        for (int i = 1; i < N; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }
        int max = Integer.MIN_VALUE;
        // 使用单调栈记录当前子数组的左右边界
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            // 单调栈数组维护，从小到大的数据顺序
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                Integer pop = stack.pop();
                // 获取以pop元素为最小值的子数组的范围的累加和*最小值
                // 这里的 i - 1其实就是j
                int cur = (stack.isEmpty() ? sum[i - 1] : sum[i - 1] - sum[stack.peek()]) * arr[pop];
                max = Math.max(max, cur);
            }
            stack.push(i);
        }
        // 判断是否栈中是否还有元素
        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            // 如果这时候栈为空，则表示当前元素是栈中最小的元素，则左右边界都可以获取到最远
            int cur = (stack.isEmpty() ? sum[N - 1] : sum[N - 1] - sum[stack.peek()]) * arr[pop];
            max = Math.max(max, cur);
        }
        return max;
    }


    private int[] gerenareRondomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }

}
