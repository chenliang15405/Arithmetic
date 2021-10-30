package com.arithmetic.features.arithmetic.interview.bfprt;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 窗口问题， 通过窗口解决数组的求值问题
 *
 * 窗口内的最大值更新结构
 *
 * @author chenliang11
 * @date 2021/1/16 12:29
 */
public class MaxWindowValue {


    @Test
    public void getMaxWindowTest() {
        int[] arr = {3,5,5,1,7,8,5,1,2,9};
        int w = 3;
        int[] maxWindow = getMaxWindow(arr, 3);

        System.out.println(Arrays.toString(maxWindow));
    }

    /**
     *  获取指定窗口大小中的每次的最大值，窗口从左向右移动，每次移动1位，则一共产生n-w+1个最大值
     *
     *  有一个整型数组arr和一个大小为w的窗口从数组的最左边滑动到最右边，窗口每次向右滑一个位置
     *  如果数组的长度为n，窗口的大小为w, 则一共产生n-w+1个窗口的最大值
     *  请实现一个函数
     *      输入：整型数组arr，窗口的大小为w
     *      输出：一个长度为n-w+1的数组res,res[i]表示每一种窗口状态下的最大值
     *
     */
    public int[] getMaxWindow(int[] arr, int w) {
        if(w < 0 || w > arr.length) {
            return arr;
        }
        // 双端链表中保存的是数据的索引，不保存数据
        // 链表中保存的是从头到尾结点，从大到小，如果需要加入的元素>链表中的元素，那么就将链表中的元素弹出
        LinkedList<Integer> qMax = new LinkedList<>();
        int[] res = new int[arr.length - w + 1];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            // 判断当前链表中的最后一个值和当前插入的值的大小
            while (!qMax.isEmpty() && arr[qMax.peekLast()] <= arr[i]) {
                qMax.pollLast();
            }
            qMax.addLast(i);
            // 判断链表中的头数据是否过期，因为窗口会向右移动，判断当前元素是否在窗口之外
            // i - w 表示当前窗口的左边界的索引位置，如果和链表中的头结点相等，那么就弹出，因为已经将当前位置元素加入到链表中了
            if(qMax.peekFirst() == i - w) {
                qMax.pollFirst();
            }
            // 判断当前是否形成w个元素的窗口, 当形成窗口的时候就开始记录最大值
            if(i >= w - 1) {
                res[index++] = arr[qMax.peekFirst()];
            }
        }
        return res;
    }

    /**
     * 获取当前数组中所有的子数组的数量，这个子数组需要满足它的最大值 - 最小值 <= num
     */
    @Test
    public void test1() {
        int[] arr = {9,2,3,1,3,5,1};
        int num = 3;
        int count = getAllLessNumSubArray(arr, num);
        System.out.println(count);
    }


    /**
     * 获取当前数组中所有的子数组的数量，这个子数组需要满足它的最大值 - 最小值 <= num
     *
     * 最大值减去最小值小于或等于num的子数组数量
     * 给定数组arr和整数num,共返回有多少个子数组满足如下情况，max(arr[i..j]) - min(arr[i..j]) <= num
     *
     * 如果数组的长度为N，请实现时间复杂度为O(N)的解法
     *
     * 1. O(N ^ 3)
     * 2. O(N)
     *
     */
    public int getAllLessNumSubArray(int[] arr, int num) {
        if(arr == null || arr.length == 0) {
            return 0;
        }
        LinkedList<Integer> qmax = new LinkedList<>();
        LinkedList<Integer> qmin = new LinkedList<>();
        int start = 0;
        int end = 0;
        int res = 0;

        while (start < arr.length) {
            // 当end到达最后一个元素，那么start-end就是有效数组，只需要将start将后移动，并且统计有效子数组的个数，每移动一位，则统计一次
            while (end < arr.length) {
                // 判断当前的链表中最后一位的元素和需要加入的元素的大小，记录当前范围内的最小值的索引到链表中
                while(!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[end]) {
                    qmin.pollLast();
                }
                qmin.addLast(end);
                // 记录当前范围的最大值的索引到链表中
                while(!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[end]) {
                    qmax.pollLast();
                }
                qmax.addLast(end);
                // 判断当前的元素是否符合，如果当前的范围不符合，那么后面的所有范围都不符合，因为max-min > num，
                // 那么后面的元素只能出现比max更大的，比min更小的，所以肯定也是不符合的
                if(arr[qmax.getFirst()] - arr[qmin.getFirst()] > num) {
                    break;
                }
                // 让当前的索引向后移动一位
                end++;
            }
            // start的一个元素的遍历已经完成，将start的索引向后移动一位
            // 需要将链表中记录的当前的start索引去除
            if(qmin.peekFirst() == start) {
                qmin.pollFirst();
            }
            if(qmax.peekFirst() == start) {
                qmax.pollFirst();
            }
            // 记录当前符合条件的所有数量，因为记录了start和end，作为有效的数组范围，那么在该范围内的所有元素都是有效的
            // 因为直到该元素都有效，那么之前的所有元素都是符合条件的，到该元素的下一个元素才不符合条件，否则不可能到达该元素位置
            res += end - start;
            // start索引向后移动一位
            // 并且end无需改变，因为从start到end如果是有效的，那么start到end中间的所有子数组都是符合条件的，所以只需要向后扩展元素即可
            // 因为start-end的最大值和最小值相减都是<=num，那么中间的元素只可能比max小，比min大，那么肯定是<=num
            start++;
        }
        return res;
    }


    @Test
    public void test2() {
        int[] arr = {9,2,3,1,3,5,1};
        int num = 3;
        int count = getAllLessNumSubArrayWithOneMethod(arr, num);
        System.out.println(count);
    }

    /**
     * O(N ^ 3)实现获取数组的 最大值 - 最小值 <= num 的子数组的数量
     */
    public int getAllLessNumSubArrayWithOneMethod(int[] arr, int num) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if(isValid(arr, i, j, num)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isValid(int[] arr, int start, int end, int num) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = start; i <= end; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }
        return max - min <= num;
    }

}
