package com.arithmetic.features.arithmetic;

import org.junit.Test;

/**
 * @author alan.chen
 * @date 2020/5/7 5:16 PM
 */
public class SmallSum {

    /**
     * 小和问题
     *
     * 在一个数组中，每一个数左边比当前数小的数累加起来，叫做这个数组的小和
     *
     * [1, 3, 4, 2, 5]
     * 1 左边比1小的数，没有
     * 3 左边比3小的数 1
     * 4 左边比4小的数 1 3
     * 2 左边比2小的数 1
     * 5 左边比5小的数 1 3 4 2
     * 所以小和：1+1+3+1+1+3+4+2 = 16
     *
     */

    private static int[] arr = {1, 3, 4, 2, 5};

    /**
     * 小和，时间复杂度O(N^2) 两次遍历
     */
    @Test
    public void smallSum() {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if(arr[j] > arr[i]) {
                    sum += arr[i];
                }
            }
        }
        System.out.println("小和：" + sum);
    }

    /**
     * 归并排序实现小和
     */
    @Test
    public void smallSum2() {
        int smallSum = mergeSort(arr, 0, arr.length - 1);
        System.out.println("小和" + smallSum);
    }


    private int mergeSort(int[] arr, int L, int R) {
        if(L == R) {
            return 0;
        }
        int mid = L + ((R - L) >> 1); // 相当于 L + (R - L) / 2 求中位数，这样防止溢出
        int sumLeft = mergeSort(arr, L, mid);
        int sumRight = mergeSort(arr, mid+1, R);
        int sum = merge(arr, L, mid, R);
        return sumLeft + sumRight + sum;
        //return mergeSort(arr, L, mid) + mergeSort(arr, mid+1, R) + merge(arr, L, mid, R);
    }

    private int merge(int[] arr, int l, int mid, int r) {
        int[] temp = new int[r - l + 1];
        int t = 0;
        int p1 = l;
        int p2 = mid + 1;
        int sum = 0;

        while (p1 <= mid && p2 <= r) {
            if(arr[p1] < arr[p2]) {
                sum += (r - p2 + 1) * arr[p1];
                temp[t++] = arr[p1++];
            } else {
                temp[t++] = arr[p2++];
            }
            //sum += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] :0;
            //temp[t++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= mid) {
            temp[t++] = arr[p1++];
        }
        while(p2 <= r) {
            temp[t++] = arr[p2++];
        }

        for (int i = 0; i < temp.length; i++) {
            arr[l + i] = temp[i];
        }

        return sum;
    }


    @Test
    public void smallSum3() {
        int sum = smallMerge(arr, 0, arr.length - 1);

        System.out.println(sum);
    }

    private int smallMerge(int[] arr, int l, int r) {
        if(l == r) {
            return 0;
        }
        int mid = l + (r - l) / 2;
        int sumLeft = smallMerge(arr, l, mid);
        int sumRight = smallMerge(arr, mid + 1, r);
        int sum = merge3(arr, l, mid , r);

        return sumLeft + sumRight + sum;
    }

    private int merge3(int[] arr, int l, int mid, int r) {
        int[] temp = new int[r - l + 1];
        int p1 = l;
        int p2 = mid + 1;
        int t = 0;
        int sum = 0;

        while(p1 <= mid && p2 <= r) {
            if(arr[p1] < arr[p2]) {
                sum += (r - p2 + 1) * arr[p1];
                temp[t++] = arr[p1++];
            } else {
                temp[t++] = arr[p2++];
            }
        }

        while(p1 <= mid) {
            temp[t++] = arr[p1++];
        }
        while(p2 <= r) {
            temp[t++] = arr[p2++];
        }
        t = 0;
        while(l < r) {
            arr[l++] = temp[t++];
        }
        return sum;
    }

}
