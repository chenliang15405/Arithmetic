package com.github.左神算法.单调栈;

import org.junit.Test;

import java.util.Stack;

/**
 * @author tangsong
 * @date 2021/7/25 0:33
 */
public class Code05_SumOfSubarrayMinimums {

    /**
     * 给定一个数组arr
     * 返回所有子数组最小值的累加和
     *
     * 思路：
     *  1.以某个元素为最小值时，可以组成有多少个子数组，然后通过子数组的数量*这个元素的值，就是当前元素可以组成的若干个子数组的最小值的和
     *  2.依次计算每个元素作为最小值时的最小值的累加和，就是总的子数组的最小值的累加和
     *  使用单调栈进行计算，麻烦的是对相同元素的处理
     *
     */
    @Test
    public void test() {
        int maxLen = 100;
        int maxValue = 50;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = subArrayMinSum1(arr);
            int ans2 = subArrayMinSum2(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

    /**
     * 暴力法
     *
     */
    public int subArrayMinSum1(int[] arr) {
        int min = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int curMin = arr[i];
                // 计算每一个子数组的最小值，并进行累加即可
                for (int k = i + 1; k <= j; k++) {
                    curMin = Math.min(curMin, arr[k]);
                }
                min += curMin;
            }
        }
        return min;
    }


    /**
     * 单调栈
     *
     *   1. 利用单调栈计算出每个元素左边<=的位置，右边<的位置，在这个范围内的所有数组，可以直接计算出有多少个以当前值为最小值的子数组，然后乘以当前元素（最小值）就是一种累加和
     *   2. 对每个元素都认为是最小值，计算对应的最小值的累加和，总的累加起来就是总的最小值的累加和
     *
     */
    public int subArrayMinSum2(int[] arr) {
        // 先计算出每个元素的左边的边界和右边的边界
        int[] left = nearLessEqualLeft(arr);
        int[] right = nearLessRight(arr);
        long min = 0;

        // 计算每个元素作为最小值时，可以获取到的最小值的累加和
        for (int i = 0; i < arr.length; i++) {
            int start = i - left[i]; // 左边的小于等于当前元素的位置到当前元素的数据个数
            int end = right[i] - i; // 右边的小于当前元素的位置到当前元素之间元素的个数
            min += (long) start * end * arr[i];
            min %= 1000000007;
        }
        return (int) min;
    }

    /**
     * 单调栈计算每个元素右边的边界
     *   处理相同元素的情况，因为左边的边界不包含相同的元素，那么右边的边界就需要包含相同的元素，只有小于的时候才会出栈
     *   这样处理可以保证左边相同元素的左边界将右边相同元素的右边处理到，否则后面的相同元素无法处理左边相同元素的左边的元素，会少计算几个子数组
     *
     */
    private int[] nearLessRight(int[] arr) {
        int N = arr.length;
        int[] right = new int[N];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                right[stack.pop()] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            right[stack.pop()] = N;
        }
        return right;
    }

    /**
     * 单调栈计算每个元素的左边的边界
     *    处理相同元素的情况 -> 小于等于时就停，就认为已经到达边界了
     *
     */
    private int[] nearLessEqualLeft(int[] arr) {
        int N = arr.length;
        int[] left = new int[N];
        Stack<Integer> stack = new Stack<>();

        for (int i = N - 1; i >= 0; i--) {
            // 当左边的元素小于等于栈顶元素时，弹出
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                left[stack.pop()] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            left[stack.pop()] = -1;
        }
        return left;
    }



    // for test
    public static int[] randomArray(int len, int maxValue) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue) + 1;
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
