package com.arithmetic.features.issue;

import org.junit.Test;

import java.util.PriorityQueue;

/**
 * @author alan.chen
 * @date 2020/6/22 5:45 PM
 */
public class KNum {

    private static int[] arr = {50, 3, 22, 99, 90, 567, 999, 66, 123, 567};

    /**
     * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，
     * 而不是第 k 个不同的元素
     *
     * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
     * 输出: 4
     */
    @Test
    public void test() {
        int kthLargest = findKthLargest(arr, 3);

        System.out.println(kthLargest);
    }

    public int findKthLargest(int[] nums, int k) {
        // 构建堆
        for(int i = 0; i < k; i++) {
            int index = i;
            while(nums[index] < nums[(index - 1) / 2]) {
                swap(nums, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        // 和堆顶比较，大于等于则交换，小则不交换
        for(int i = k; i < nums.length; i++) {
            if(nums[i] >= nums[0]) {
                // 交换堆顶和当前元素
                swap(nums, 0, i);
                // 调整小顶堆
                heapify(nums, 0, k);
            }
        }

        // 依次比较完成后，第0个元素（堆顶）就是第k大的元素，即小顶堆的堆顶，如果要求topK，则整个堆就是前k大的元素，这个只要求第k大的元素
        return nums[0];
    }

    // 堆化
    private void heapify(int[] nums, int i, int R) {
        int left = i * 2 + 1;
        while(left < R) {
            // 左右孩子中比较一个最小的
            int samllest = left + 1 < R && nums[left + 1] < nums[left] ? left + 1 : left;
            // 比较当前节点和最小的孩子节点的大小
            samllest = nums[i] < nums[samllest] ? i : samllest;
            // 如果当前的堆顶已经是最小的，直接break
            if(i == samllest) {
                break;
            }
            // 否则交换，并向下依次比较
            swap(nums, i, samllest);
            i = samllest;
            left = i * 2 + 1;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
