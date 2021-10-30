package com.github.左神算法.滑动窗口;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author tangsong
 * @date 2021/7/11 0:31
 */
public class SlidingWindowMaxArray {

    /**
     * 假设一个固定大小为W的窗口，依次划过arr，返回每一次划出状况的最大值
     * 例如：arr = [4,3,5,4,3,3,6,7] W = 3
     *  返回 [5,5,5,4,6,7]
     *
     */
    @Test
    public void test() {
        int[] arr = {4, 3, 5, 4, 3, 3, 6, 7};
        int W = 3;
        System.out.println(Arrays.toString(way1(arr, W)));
        System.out.println(Arrays.toString(getMaxWindow(arr, W)));
    }

    public int[] way1(int[] arr, int W) {
        if (arr == null || arr.length <= 0) {
            return null;
        }
        int N = arr.length;
        int[] ans = new int[N - W + 1];
        int L = 0;
        for (int i = W - 1; i < N; i++) {
            int max = arr[L];
            for (int k = L; k <= i; k++) {
                max = Math.max(max, arr[k]);
            }
            ans[L] = max;
            L++;
        }
        return ans;
    }

    /**
     *  滑动窗口
     *    使用滑动窗口 -> 维护一个最大值的队列，使用队列来记录当前的最大值，并且在窗口滑动的过程中，将过期的元素移出窗口
     *
     */
    public int[] getMaxWindow(int[] arr, int W) {
        if (arr == null || arr.length <= 0) {
            return null;
        }
        int N = arr.length;
        int[] ans = new int[N - W + 1];
        int index = 0;
        // 建立一个维护窗口的队列, 窗口记录的是元素的索引，因为需要判断当前的索引是否需要过期（移出窗口）
        LinkedList<Integer> maxWindow = new LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            // 判断当前的窗口的尾和当前的值的大小
            while (!maxWindow.isEmpty() && arr[maxWindow.peekLast()] <= arr[i]) {
                maxWindow.pollLast();
            }
            // 将当前元素加入队列
            maxWindow.addLast(i);
            // 判断窗口是否形成，如果形成，就收集数据
            if (i >= W - 1) {
                ans[index++] = arr[maxWindow.peekFirst()];
            }
            // 判断当前元素是否过期
            if (maxWindow.peekFirst() <= i - W + 1) {
                maxWindow.pollFirst();
            }
        }
        return ans;
    }

}
