package com.arithmetic.features.arithmetic;

import org.junit.Test;
import sun.applet.Main;

import java.util.Arrays;

/**
 * 希尔排序是把记录按下表的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，
 * 每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。
 *
 * @author alan.chen
 * @date 2020/4/26 9:50 PM
 */
public class ShellSort {

    private static int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};


    /**
     * 希尔排序
     */
    @Test
    public void shellSort() {
        int length = array.length;

        int gap = length / 2; // 希尔增量设置2

        while (gap > 0) {
            // 每次分为诺干个数组都对分成的数组进行内部比较
            for (int i = gap; i < array.length; i++) {
                int preIndex = i - gap;
                int cur = i;
                while (preIndex >= 0) {
                    if (array[preIndex] > array[cur]) {
                        swap(array, i - gap, i);
                        cur = preIndex;
                    }
                    preIndex -= gap;
                }
            }
            // 继续处理希尔增量，变为排序一次的数组按照gap/2再分为gap个数组
            gap /= 2;
        }
        Arrays.stream(array).forEach(System.out::println);
    }


    /**
     * 希尔排序
     */
    @Test
    public void shellSort2() {
        int length = array.length;
        int temp, gap = length / 2;

        while (gap > 0) {

            for (int i = gap; i < array.length; i++) {

                temp = array[i];
                int preIndex = i - gap;

                while (preIndex >= 0 && array[preIndex] > temp) {
                    // 如果分成的小数组中，前一个数大于后面一个数，则前后交换
                    array[preIndex + gap] = array[preIndex]; // 其中preIndex+gap 就是i
                    preIndex -= gap;
                }
                array[preIndex + gap] = temp; // preIndex先-gap结束上面的while，下面加gap就是恢复到i-gap
            }
            gap /= 2;
        }
        Arrays.stream(array).forEach(System.out::println);
    }


    @Test
    public void test() {
        int length = array.length;
        int gap = length / 2;

        while (gap > 0) {
            for (int i = gap; i < array.length; i++) {
                int preIndex = i - gap;
                int cur = i;
                while (preIndex >= 0) {
                    if (array[preIndex] > array[cur]) {
                        swap(array, preIndex, cur);
                        cur = preIndex;
                    }
                    preIndex -= gap;
                }
            }
            gap /= 2;
        }
        Arrays.stream(array).forEach(System.out::println);
    }


    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    @Test
    public void test5() {
        int length = array.length;
        int gap = 2;

        while(gap > 0) {
            for (int i = 0; i < array.length; i++) {
                int pre = i - gap;
                int cur = i;
                while(pre >= 0) {
                    if(array[pre] > array[cur]) {
                        swap(array, pre, cur);
                        cur = pre;
                    }
                    pre -= gap;
                }
            }
            gap /= 2;
        }
        Arrays.stream(array).forEach(System.out::println);
    }

}
