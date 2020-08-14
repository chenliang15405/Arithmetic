package com.arithmetic.features.arithmetic;

import org.junit.Test;

import java.util.Arrays;

/**
 *
 * 把长度为n的输入序列分成两个长度为n/2的子序列；对这两个子序列分别采用归并排序；将两个排序好的子序列合并成一个最终的排序序列。
 *
 * 最佳情况：T(n) = O(n) 最差情况：T(n) = O(nlogn) 平均情况：T(n) = O(nlogn)
 *
 * @author alan.chen
 * @date 2020/4/27 9:32 PM
 */
public class MergeSort {

    private static int[] array = {3, 45, 58, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};


    @Test
    public void mergeSort() {
        int[] temp = new int[array.length];
        sort(array, 0, array.length - 1, temp);

        Arrays.stream(array).forEach(System.out::println);
    }

    // 定义方法排序，初始左索引为0，右索引为array.length-1 就是最左和最右
    private void sort(int[] array, int left, int right, int[] temp) {
        if (left == right) {
            return;
        }

        int mid = (left + right) / 2;

        // 分治法
        //对左边进行排序
        sort(array, left, mid, temp);
        // 对右边进行排序
        sort(array, mid + 1, right, temp);
        // 对递归的每一次sort进行merge
        merge(array, left, mid, right, temp);
    }

    /**
     * 每次都是将数组分为2个小数组，一直分，直到分为2个数组，每个数组都是1个元素的时候停止递归，开始merge
     * 对这个左数组为1个元素和右数组为1个元素进行merge(就是排序)，然后merge完成，得到一个2个元素的数组，
     * 开始和上一个递归产生的2个小数组，并且都是2个元素的数组开始merge，每次都是有左边的索引指向左边数组的第1个元素，
     * 右边的索引(mid+1)指向右边数组的第一个元素，开始比较，向临时数组中插入排序好的数据，然后再将临时数组copy回原数组
     *
     * 一直到最后一个merge, 将整体的数组分为2个小数组（经过之前的递归merge，左右两个小数组自身已经是有序的），
     * 一个左边的数组，一个右边的数组，left指向的0，right指向是array.length-1，mid指的是中间值(left+right/2)，
     * 然后设置i和j分别指向左边数组和右边数组，开始比较，直到有一边的数组索引指向该数组的最大值，
     * 将另一个数组剩余值直接可以填充到临时数组的最后面，最后再复制回原数组。
     *
     *
     */
    private void merge(int[] array, int left, int mid, int right, int[] temp) {
        int i = left;
        int j = mid + 1;
        int t = 0;
        // 比较左右两部分的元素，哪个小，把那个元素填入temp中
        while (i <= mid && j <= right) {
            if (array[i] > array[j]) {
                temp[t++] = array[j++];
            } else {
                temp[t++] = array[i++];
            }
        }
        // 上面的循环退出后，把剩余的元素依次填入到temp中
        // 以下两个while只有一个会执行
        while (i <= mid) {
            temp[t++] = array[i++];
        }
        while (j <= right) {
            temp[t++] = array[j++];
        }

        // 复制回原数组
        t = 0;
        while (left <= right) {
            array[left++] = temp[t++];
        }
    }


    @Test
    public void test() {
        int[] temp = new int[array.length];
        sort2(array, 0, array.length - 1, temp);

        Arrays.stream(array).forEach(System.out::println);
    }

    private void sort2(int[] array, int L, int R, int[] temp) {
        if(L >= R) {
            return;
        }

        int mid = (L + R)/2;
        sort2(array, L, mid, temp);
        sort2(array, mid+1, R, temp);
        merge2(array, L, mid ,R, temp);
    }

    private void merge2(int[] array, int l, int mid, int r, int[] temp) {
        int p1 = l;
        int p2 = mid +1;
        int t = 0;

        while (p1 <= mid && p2 <= r) {
            if(array[p1] < array[p2]) {
                temp[t++] = array[p1++];
            } else {
                temp[t++] = array[p2++];
            }
        }

        while (p1 <= mid) {
            temp[t++] = array[p1++];
        }
        while(p2 <= r) {
            temp[t++] = array[p2++];
        }

        t = 0;
        while (l <= r) {
            array[l++] = temp[t++];
        }
    }


    @Test
    public void test3() {
        sort3(array, 0, array.length - 1);

        Arrays.stream(array).forEach(System.out::println);
    }

    private void sort3(int[] array, int l, int r) {
        if(l >= r) {
            return;
        }
        int mid = (r + l) / 2;
        sort3(array, l, mid);
        sort3(array, mid + 1, r);
        merge3(array, l, mid, r);
    }

    private void merge3(int[] array, int l, int mid, int r) {
        int p1 = l;
        int p2 = mid + 1;
        int[] temp = new int[array.length];
        int t = 0;

        while (p1 <= mid && p2 <= r) {
            if(array[p1] < array[p2]) {
                temp[t++] = array[p1++];
            } else {
                temp[t++] = array[p2++];
            }
        }

        while (p1 <= mid) {
            temp[t++] = array[p1++];
        }
        while (p2 <= r) {
            temp[t++] = array[p2++];
        }

        t = 0;
        while(l <= r) {
            array[l++] = temp[t++];
        }
    }


    @Test
    public void test5() {
        sort5(array, 0, array.length - 1);

        Arrays.stream(array).forEach(System.out::println);
    }


    @Test
    public void test6() {
        mergeSort2(array, 0, array.length - 1);

        Arrays.stream(array).forEach(System.out::println);
    }


    private void mergeSort2(int[] array, int left, int right) {
        if(left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort2(array, left, mid);
        mergeSort2(array, mid + 1, right);
        merge6(array, left, mid, right);
    }

    private void merge6(int[] array, int left, int mid, int right) {
        int p1 = left;
        int p2 = mid + 1;
        int[] temp = new int[right - left + 1];
        int t = 0;

        while (p1 <= mid && p2 <= right) {
            if(array[p1] <= array[p2]) {
                temp[t++] = array[p1++];
            } else {
                temp[t++] = array[p2++];
            }
        }

        while(p1 <= mid) {
            temp[t++] = array[p1++];
        }
        while(p2 <= right) {
            temp[t++] = array[p2++];
        }
        t = 0;
        while(left <= right) {
            array[left++] = temp[t++];
        }
    }

    private void sort5(int[] array, int l, int r) {
        if(l >= r) {
            return;
        }
        int mid = l + (r - l) / 2;
        sort5(array, l, mid);
        sort5(array, mid + 1, r);
        merge5(array, l, mid, r);
    }

    private void merge5(int[] array, int l, int mid, int r) {
        int[] temp = new int[array.length];
        int p1 = l;
        int p2 = mid + 1;
        int t = 0;

        while(p1 <= mid && p2 <= r) {
            if(array[p1] <= array[p2]) {
                temp[t++] = array[p1++];
            } else {
                temp[t++] = array[p2++];
            }
        }
        while(p1 <= mid) {
            temp[t++] = array[p1++];
        }
        while(p2 <= r) {
            temp[t++] = array[p2++];
        }
        t = 0;
        while(l <= r) {
            array[l++] = temp[t++];
        }
    }


}