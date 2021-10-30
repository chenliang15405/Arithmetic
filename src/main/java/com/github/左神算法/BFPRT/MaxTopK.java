package com.github.左神算法.BFPRT;

import com.sun.xml.internal.ws.util.exception.LocatableWebServiceException;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author tangsong
 * @date 2021/8/16 2:01
 */
public class MaxTopK {

    /**
     * 一共给定N个数，获取前K个最大的数
     *
     *  三种解法：
     *   1. 排序
     *   2. 小根堆
     *   3. 前K大，就是先计算第N-K小的数，然后将比第N-K小的数收集
     */
    @Test
    public void test() {
        int testTime = 10000;
        int maxSize = 100;
        int maxValue = 100;
        boolean pass = true;
        System.out.println("测试开始，没有打印出错信息说明测试通过");
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = generateRandomArray(maxSize, maxValue);

//            System.out.println("1" + Arrays.toString(arr));
//            System.out.println(k);

            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);

            int[] ans1 = maxTopK1(arr1, k);
            int[] ans2 = maxTopK2(arr2, k);
            int[] ans3 = maxTopK3(arr3, k);
            if (!isEqual(ans1, ans2) || !isEqual(ans1, ans3)) {
                pass = false;
                System.out.println("出错了！");
                printArray(ans1);
                printArray(ans2);
                printArray(ans3);
                break;
            }
        }
        System.out.println("测试结束了，测试了" + testTime + "组，是否所有测试用例都通过？" + (pass ? "是" : "否"));
    }


    /**
     * 排序 + 收集
     *  时间复杂度：O(N*logN)
     *
     */
    public int[] maxTopK1(int[] arr, int k) {
        if(arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        k = Math.min(N, k);
        Arrays.sort(arr);

        int index = 0;
        int[] ans = new int[k];

        for (int i = arr.length - 1; i >= 0 && index < k; i--) {
            ans[index++] = arr[i];
        }
        return ans;
    }

    /**
     * 小根堆
     *  时间复杂度：O(N + K*logN)
     *
     *   返回的不是有序的数据，所以在最后需要单独排序
     *
     */
    public int[] maxTopK2(int[] arr, int k) {
        if(arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        k = Math.min(N, k);
        // 建立k个数的小根堆
        for (int i = 0; i < k; i++) {
            int index = i;
            while (arr[index] < arr[(index - 1) / 2]) {
                swap(arr,  index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        for (int i = k; i < arr.length; i++) {
            // 如果大于堆顶，则调整堆
            if(arr[i] > arr[0]) {
                swap(arr, i, 0);
                heapify(arr, 0, k);
            }
        }

        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = arr[i];
        }
        Arrays.sort(ans);
        for (int L = 0, R = k - 1; L < R; L++, R--) {
            swap(ans, L, R);
        }

        return ans;
    }

    private void heapify(int[] arr, int index, int k) {
        int left = index * 2 + 1;
        while (left < k) {
            int right = index * 2 + 2;
            int lowest = right < k && arr[right] < arr[left] ? right : left;
            if(arr[index] < arr[lowest]) {
                break;
            }
            swap(arr, index, lowest);
            index = lowest;
            left = index * 2 + 1;
        }
    }


    /**
     * 利用改进的快排的方式，前k大的数，那么先找到第N-K小的数，然后遍历数组，只要比第N-K小的数大，那么就是前K大的所有数
     *
     * 时间复杂度O(n + k * logk)
     *
     */
    public int[] maxTopK3(int[] arr, int k) {
        if(arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        k = Math.min(N, k);
        int[] ans = new int[k];

        // 找到第N-K小的数，因为是根据索引进行查找，所以需要额外-1
        int minNum = minKth(arr, N - k);

        int index = 0;
        // 遍历数组，找到所有比第N-K小的数大的所有的数
        for (int i = 0; i < N; i++) {
            // 如果数组中有多个相等的数，但是这里比较的是具体的值，所以可能会超过k个，所以这里只匹配小的数，在下面补充相等的数
            if(arr[i] > minNum) {
                ans[index++] = arr[i];
            }
        }
        // 如果数据填充之后，还没有达到K个数，则加入第N-k小的数，否则少一个数，因为前面计算第N-K小的数的时候是按照下标进行查找的
        // 那么在找第K大的数时，可能会少一个符合条件的数
        // 因为可能数组中有多个相同条件的数，所以在匹配完成小的数之后填充相等的数
        while(index < k) {
            ans[index++] = minNum;
        }

        Arrays.sort(ans);
        for (int i = 0; i < ans.length / 2; i++) {
            swap(ans, i, ans.length - 1 - i);
        }

        return ans;
    }

    /**
     * 找到第i小的数
     *
     */
    private int minKth(int[] arr, int index) {
        int L = 0;
        int R = arr.length - 1;
        int pivot = 0;
        int[] range = null;
        while (L < R) {
            // 随机选择一个划分值
            pivot = arr[L + (int) (Math.random() * (R - L + 1))];
            range = partition(arr, L, R, pivot);
            if (index < range[0]) {
                R = range[0] - 1;
            } else if (index > range[1]) {
                L = range[1] + 1;
            } else {
                return pivot;
            }
        }
        return arr[L];
    }

    private int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[] { less + 1, more - 1 };
    }


    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }



    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
