package com.arithmetic.features.arithmetic.interview.bfprt;

import org.junit.Test;

import java.util.Arrays;

/**
 * BFPRT算法，获取比K小的数
 * O(N)时间复杂度
 *
 * @author chenliang11
 * @date 2021/1/16 11:47
 */
public class BFPRT_Test {


    @Test
    public void getKNumTest() {
        int[] arr = {10, 5, 6, 7, 3, 13, 2, 3, 65, 88, 5, 32, 7, 6, 8, 34, 33, 1, 5, 6};
        int k = 3;
        int[] nums = getMinKNumsByHeap(arr, k);

        System.out.println(Arrays.toString(nums));
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 获取比K小的所有数   by 堆
     *
     * 构建大顶堆，然后比堆顶小的数进入堆，整个堆就是比第K小的
     * 所有数，堆顶即第K小的数
     *
     * O(N*logN)
     */
    public int[] getMinKNumsByHeap(int[] arr, int k) {
        if(k < 1 || k > arr.length) {
            return arr;
        }
        int[] kHeap = new int[k];
        for (int i = 0; i < k; i++) {
            heapInsert(kHeap, arr[i], i);
        }

        // 堆化
        for (int i = k; i < arr.length; i++) {
            if(arr[i] < kHeap[0]) {
                kHeap[0] = arr[i];
                heapify(kHeap, 0, k);
            }
        }
        // topk问题，返回第k小个数，如果需要第K小的数，那么大顶堆的堆顶就是 kHeap[0]
        return kHeap;
    }

    private void heapify(int[] kHeap, int i, int k) {
        int left = i * 2 + 1;
        int right = i * 2 + 2;
        int largest = i;
        while (left < k) {
            if(right < k && kHeap[left] < kHeap[right]) {
                largest = right;
            } else {
                largest = left;
            }
            if(largest == i) {
                break;
            }
            swap(kHeap, largest, i);
            i = largest;
            left = i * 2 + 1;
        }
    }

    private void heapInsert(int[] kHeap, int value, int index) {
        kHeap[index] = value;
        while (index > 0) {
            int parent = (index - 1) / 2;
            if(kHeap[index] > kHeap[parent]) {
                swap(kHeap, index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }


    /**
     * BFPRT算法计算第K小的数
     *
     */
    @Test
    public void bfprt() {
        int[] arr = {10, 5, 6, 7, 3, 13, 2, 3, 65, 88, 5, 32, 7, 6, 8, 34, 33, 1, 5, 6};
        int k = 3;
        int minKNumByBFPRT = getMinKNumByBFPRT(arr, k);

        System.out.println(minKNumByBFPRT);
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }


    /**
     * 实现获取第K小的数  by BFPRT
     * (如果需要比K小的所有数，则直接获取第k个数的左边的所有数)
     *
     * 在数组中选择一个数，大于的放在左边，等于的放在中间，小于的放在右边
     * 但是这个去比较的数怎么选择，如果选择的不好，那么时间复杂度较高
     * BFPRT就是解决如何选择这个数的问题
     *
     * O(N)
     */
    public int getMinKNumByBFPRT(int[] arr, int k) {
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    private int bfprt(int[] arr, int begin, int end, int k) {
        // 当数组中的元素只有一个的时候，则这个元素就是需要的中位数
        if (begin == end) {
            return arr[begin];
        }
        // 精髓 -> 获取中位数
        int pivot = medianOfMedians(arr, begin, end);
        // 将数组进行partition，获取到等于元素的边界
        int[] pivotRange = partition(arr, begin, end, pivot);
        if (k >= pivotRange[0] && k <= pivotRange[1]) {
            // 如果k的值在等于区域中，那么可以直接获取到第k个数是多少
            return arr[k];
        } else if (k < pivotRange[0]) {
            return bfprt(arr, begin, pivotRange[0] - 1, k);
        } else {
            return bfprt(arr, pivotRange[1] + 1, end, k);
        }
    }

    /**
     * 对数组进行partition，将大于pivot的数据放在左边，等于pivot的数放在中间，小于pivot的数放在右边
     *
     * arr数组中的元素已经被大致进行了3个区域的划分
     */
    private int[] partition(int[] arr, int begin, int end, int pivot) {
        int small = begin - 1;
        int cur = begin;
        int big = end + 1;
        while (cur != big) {
            if (arr[cur] < pivot) {
                swap(arr, ++small, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --big);
            } else {
                cur++;
            }
        }
        int[] range = new int[2];
        range[0] = small + 1;
        range[1] = big - 1;
        return range;
    }

    /**
     * 对整体的数组划分每5个划分为一个数组，并将数组进行组内排序，然后将每个小数组的中位数组成一个数组，如果大于5，那么继续递归bfprt继续
     * 划分数组，如果不足5个，那么在下一次的bfprt递归中，将获取到一个该数组的中位数，可以直接获取到需要的中位数，可以继续向下对原数组进行操作
     */
    private int medianOfMedians(int[] arr, int begin, int end) {
        // 数组中的元素个数
        int num = end - begin + 1;
        // 将数组划分为每5个一组
        int offset = num % 5 == 0 ? 0 : 1;
        int[] mArr = new int[num / 5 + offset];
        for (int i = 0; i < mArr.length; i++) {
            int beginI = begin + i * 5;
            int endI = beginI + 4;
            mArr[i] = getMedian(arr, beginI, Math.min(end, endI));
        }
        // 对获取到的中位数的数组递归进行bfprt，直到数组的个数不足5个，直接获取到中位数
        // 如果数组中的元素不足5个，那么第二次递归进行bfprt的时候，在这里传递到bfprt中的时候，mArr中只有一个元素->中位数，在上面的递归终止条件中可获取到
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    /**
     * 对划分为5个每组的数据进行组内排序，并返回该数组中的中位数
     */
    private int getMedian(int[] arr, int begin, int end) {
        insertionSort(arr, begin, end);
        int sum = end + begin;
        int mid = (sum / 2) + (sum % 2);
        return arr[mid];
    }

    /**
     * 对数组进行排序
     */
    private void insertionSort(int[] arr, int begin, int end) {
        for (int i = begin + 1; i < end + 1; i++) {
            for (int j = i; j > begin; j--) {
                if (arr[j - 1] > arr[j]) {
                    swap(arr, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
