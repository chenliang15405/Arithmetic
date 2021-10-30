package com.github.左神算法.BFPRT;

import org.junit.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author tangsong
 * @date 2021/8/8 13:16
 */
public class BFPRT {

    /**
     * 在无序数组中求第K小的数
     *  1. 改写快排的方法
     *  2. bfprt
     *
     */

    /**
     * bfprt算法：在无序数组中找到第k小的数  O(N)
     *
     * bfprt思路:
     *   1. 五个数一组
     *   2. 每个小组内进行排序
     *   3. 从每个小组中挑选出中位数组成一个新的数组
     *   4. 从新的数组中找到中位数
     *   5. 使用这个选择的中位数进行荷兰国旗问题（对原数组进行划分，< = >, 然后递归进行划分）
     *
     */
    @Test
    public void test() {
        // 1 12 13 16 23 35 37
//        int[] arr = {23, 12, 35, 1, 37, 13, 16};
//        int k = 3;
//
//        //69
//        System.out.println(minKth1(arr, k));


        int testTime = 10000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans1 != ans2) {
                System.out.println(Arrays.toString(arr));
                System.out.println(k);
                System.out.println("ans1=" + ans1);
                System.out.println("ans2=" + ans2);
                System.out.println("ans3=" + ans3);
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }


    /**
     * 堆
     *  建立k个数的大根堆，并将身下的元素依次比较，如果小于堆顶，则加入堆中，那么最终堆中肯定是最小的k个数，堆顶就是第k小的数
     *
     * 时间复杂度： O(N*logK)
     *
     */
    public int minKth1(int[] arr, int k) {
        // 建立大根堆
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        for (int i = 0; i < k; i++) {
            queue.offer(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            // 如果当前元素小于堆顶，则加入堆中
            if(arr[i] < queue.peek()) {
                queue.poll();
                queue.offer(arr[i]);
            }
        }
        return queue.peek();
    }


    /**
     * 改写快排，时间复杂度O(N)
     *
     *  将数组分为 > = < 三个区域，判断当前需要的数是否在=区域中，如果在则直接返回，如果不在，则根据区域继续划分
     */
    public int minKth2(int[] array, int k) {
        if(array == null || array.length == 0) return 0;
        int[] arr = Arrays.copyOf(array, array.length);
        return process(arr, 0, arr.length - 1, k - 1);
    }

    private int process(int[] arr, int L, int R, int k) {
        if(L > R) {
            return -1;
        };
        // 随机获取一个指定的值进行划分
        int pivot = arr[L + (int)(Math.random() * (R - L + 1))];
        int[] range = partition(arr, L, R, pivot);
        // 判断寻找的第k小的数是否在划分出来的等于区域下标中
        if(k >= range[0] && k <= range[1]) {
            return arr[range[0]];
        } else if(k < range[0]) {
            // 左边区域继续划分
            return process(arr, L, range[0] - 1, k);
        } else {
            // 右边区域继续划分
            return process(arr, range[1] + 1, R, k);
        }
    }


    /**
     * bfprt算法
     *   时间复杂度O(N)
     *
     *  核心思想：和改进快排的方式相同，只不过优化的是如何选取划分值（pivot）
     *
     */
    public int minKth3(int[] array, int k) {
        if(array == null || array.length == 0) return 0;
        int[] arr = Arrays.copyOf(array, array.length);
        return bfprt(arr, k - 1, 0, arr.length - 1);
    }

    // 按照 < = > 三个区域进行划分，不过在划分的时候，bfprt的思想是找到一个每次都是均匀划分的数
    private int bfprt(int[] arr, int k, int L, int R) {
        if(L >= R) {
            return arr[L];
        }
        // L...R 每5个数一组
        // 每个小组内部排好序
        // 取每个小组的中位数组成一个新数组
        // 这个新数组的中位数就是去划分的元素
        int pivot = mediaOfMedias(arr, L, R);
        // 拿到划分值，使用这个数进行划分
        int[] range = partition2(arr, L, R, pivot);
        if(k >= range[0] && k <= range[1]) {
            return arr[k];
        } else if(k < range[0]) {
            return bfprt(arr, k, L, range[0] - 1);
        } else {
            return bfprt(arr, k, range[1] + 1, R);
        }
    }

    // 对数组arr按照pivot进行划分 < = >
    private int[] partition2(int[] arr, int L, int R, int pivot) {
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

    // L...R 每5个数一组
    // 每个小组内部排好序
    // 取每个小组的中位数组成一个新数组
    // 这个新数组的中位数 返回
    private int mediaOfMedias(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        // 这个数组保存的就是将5个数划分为一组之后取的每个小数组的中位数，mArr[0] 第一个5个数一组的小数组的中位数
        int[] mArr = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = L + team * 5;
            // L ... L + 4
            // L + 5 ... L + 9
            // 直接获取当前这个5个数一组的中位数
            mArr[team] = getMedian(arr, teamFirst, Math.min(R, teamFirst + 4)); // R 和 teamFirst+4取小，防止溢出，因为最后可能不够5个数
        }
        // 在mArr中，找到中位数，因为这里的mArr也是无序的，并且要找的元素可以看作是计算第size/2小的数，那么直接调用bfprt即可
        return bfprt(mArr, mArr.length / 2, 0, mArr.length - 1);
    }

    // 获取在指定范围的数组的中位数
    private int getMedian(int[] arr, int L, int R) {
        insertionSort(arr, L, R); // 排序
        return arr[(L + R) / 2]; // 取中位数
    }

    // 插入排序，对于数据量小的数组排序都可以随意选择
    private void insertionSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L; j--) {
                if(arr[j + 1] < arr[j]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }


    /**
     * 在数组arr的L...R上，对p进行划分，< = > 三个区域
     *  返回相等区域的下标区域
     */
    private int[]   partition(int[] arr, int L, int R, int p) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if(arr[cur] > p) {
                swap(arr, --more, cur);
            } else if(arr[cur] < p) {
                swap(arr, ++less, cur++);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }





    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }
}
