package com.github.左神算法.滑动窗口;


import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * @author tangsong
 * @date 2021/7/11 2:05
 */
public class AllLessNumSubArray {

    /**
     * 给定一个整型数组arr，和一个整数num，某个arr中的子数组sub，如果想达标，必须满足：
     *   sub中最大值 - sub中最小值 <= num
     *   返回arr中达标子数组的数量
     *
     */
    @Test
    public void test() {
        int[] arr = {-52, -119, 98, 26, -19, 41, 35, -29, 28, -123, -3, -69, -91, 37, 19, -19, -19, -168, 172, -175, 54, 3, 130, -136, -115, 44, 198, -106, -71, -69, 124, -141, 87, 53, 25, -131, -26, -27, 114, 112, 11, 39, -20, 64, 66, 59, 135, 39, -33, -130, -87, -129, -1, -3, -100, -30, -93, 43, 143, -57, -71, 27, 115, 45, -39, -133, 137, 16, -6, 60, -51, -13, 130, -62, -5, 23, 92, 170, 101, -50, -42, 67, 82, 76, 37, -33, 8, 23};
        int sum = 71;
        System.out.println(right(arr, sum));
        System.out.println(slidWindow(arr, sum));

    }


    public int right(int[] arr, int num) {
        int length = arr.length;
        int count = 0;
        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                int max = Integer.MIN_VALUE;
                int min = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++) {
                    max = Math.max(max, arr[k]);
                    min = Math.min(min, arr[k]);
                }
                if(max - min <= num) {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * 滑动窗口解法
     *   思路： 1. 使用一个维护最大值的窗口，一个维护最小值的窗口，窗口中维护的是下标，方便后面过期某个元素
     *         2. 将整个数组中的元素都加入到两个窗口中，并按照大小维护好顺序和下标
     *         3. 从0开始遍历每个元素，查看当前的元素在窗口中有多少个符合条件的子数组
     *         4. 因为窗口每次都扩张到不满足条件，那么在这个窗口中都是满足条件的，因为这个窗口中的元素都小于<max，>min，那么肯定是否符合元素的
     *         5. 直接通过窗口的扩张下标-当前已某个元素开头的下标的长度就是符合的个数
     *         6. 对当前遍历的元素查找是否已经在窗口中，如果在，那么在下次遍历之前需要将该元素过期，便于可以继续扩张窗口的范围
     *
     */
    public int slidWindow(int[] arr, int num) {
        int N = arr.length;
        // 最小窗口和最大窗口, 只保存元素的下标
        LinkedList<Integer> maxWindow = new LinkedList<>();
        LinkedList<Integer> minWindow = new LinkedList<>();

        int R = 0; // 当前窗口扩张的范围
        int count = 0;

        for (int i = 0; i < N; i++) {
            // 先扩张窗口, 扩张到不满足条件为止，如果没有扩张到最后，那么等待遍历到某个元素，将最小窗口或者最大窗口的元素过期，可能还可以向后扩张
            while (R < N) {
                while (!maxWindow.isEmpty() && arr[maxWindow.peekLast()] <= arr[R]) {
                    maxWindow.pollLast();
                }
                maxWindow.offerLast(R);
                while (!minWindow.isEmpty() && arr[minWindow.peekLast()] >= arr[R]) {
                    minWindow.pollLast();
                }
                minWindow.offerLast(R);
                // 判断当前是否满足条件，如果不满足条件，则直接break，因为遇到不满足条件的，只要该元素在区间内，后面的永远不会满足条件
                if(arr[maxWindow.peekFirst()] - arr[minWindow.peekFirst()] > num) {
                    break;
                } else {
                    R++;
                }
            }
            // 统计当前满足需要的子数组个数
            count += R - i; // 因为窗口已经扩张到R了，那么R内的元素都是满足需要的，但是目前是从i作为数组的开始元素进行统计的，所以R-i表示所有以i位置开头满足需求的子数组

            // 判断当前是否已经过期了窗口中的元素，如果已经过期，则将该元素移出，可能窗口还会重新向后移动
            if(maxWindow.peekFirst() == i) {
                maxWindow.pollFirst();
            }
            if(minWindow.peekFirst() == i) {
                minWindow.pollFirst();
            }
        }
        return count;
    }



}
