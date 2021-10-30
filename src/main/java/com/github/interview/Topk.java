package com.github.interview;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tangsong
 * @date 2021/9/21 0:12
 */
public class Topk {

    /**
     * 对于一个无序数组，数组中元素为互不相同的整数，请返回其中最小的 k 个数，顺序与原数组中元素顺序一致。
     *
     * 测试样例：
     * [2, 1, 4, 3, 0, 87, 5], 3
     *
     * 返回：
     * [2, 1, 0]
     *
     */

    @Test
    public void test() {
        int[] arr = {2,1,4,3,0,87,5};
        int k = 3;

        int[] ints = process1(arr, k);
        int[] ints1 = process2(arr, k);

        System.out.println(Arrays.toString(ints));
        System.out.println(Arrays.toString(ints1));
    }


    /**
     * 改造快排的方式，统计第k小的所有数
     *
     */
    private int[] process2(int[] arr, int k) {
        int[] nums = Arrays.copyOf(arr, arr.length);
        int num = quickSelect(arr, 0, arr.length - 1, k - 1);

        int[] ans = new int[k];
        int index = 0;

        for (int i = 0; i < nums.length; i++) {
            if(nums[i] <= num) {
                ans[index++] = nums[i];
            }
        }

        return ans;
    }

    private int quickSelect(int[] arr, int left, int right, int k) {
        if(left > right) {
            return arr[left];
        }
        int pivot = arr[left + (int)(Math.random()*(right - left + 1))];
        int[] range = partition(arr, left, right, pivot);
        if(k >= range[0] && k <= range[1]) {
            return arr[range[0]];
        } else if(k > range[1]) {
            return quickSelect(arr, range[1] + 1, right, k);
        } else {
            return quickSelect(arr, left, range[0] - 1, k);
        }
    }

    private int[] partition(int[] arr, int left, int right, int pivot) {
        int less = left - 1;
        int more = right + 1;

        while(left < more) {
            if(arr[left] > pivot) {
                swap(arr, left, --more);
            } else if(arr[left] < pivot) {
                swap(arr, left++, ++less);
            } else{
                left++;
            }
        }
        return new int[]{less + 1, more - 1};
    }


    /**
     * 第k小的数，保持和原数组顺序一致
     *
     */
    private int[] process1(int[] nums, int k) {
        int[] arr = Arrays.copyOf(nums, nums.length);
        for (int i = 0; i < k; i++) {
            heapInsert(arr, i);
        }

        for (int i = k; i < arr.length; i++) {
            if(arr[0] > arr[i]) {
                swap(arr, 0, i);
                heapify(arr, 0, k);
            }
        }
        int[] ans = new int[k];
        int index = 0;
        // 堆顶是第k小的元素
        int minK = arr[0];
        // 遍历原数组，获取所有按顺序的数据
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] <= minK) {
                ans[index++] = nums[i];
            }
        }

        return ans;
    }

    private void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;
        while(left < heapSize) {
            int right = left + 1;
            int largest = right < heapSize && arr[right] > arr[left] ? right : left;

            if(arr[index] > arr[largest]) {
                break;
            }
            swap(arr, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }

    private void heapInsert(int[] arr, int index) {
        while(arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }


    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
