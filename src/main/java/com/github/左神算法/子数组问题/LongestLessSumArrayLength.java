package com.github.左神算法.子数组问题;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/10/19 14:11
 */
public class LongestLessSumArrayLength {

    /**
     * 给定一个整数组成的无序数组arr，值可能正、可能负、可能0，给定一个整数值k，找到arr的所有子数组里，哪个子数组的累加和
     * <=K，并且是长度最大的返回其长度
     *
     *
     */
    @Test
    public void test() {
        System.out.println("test begin");
        for (int i = 0; i < 10000000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (maxLengthAwesome(arr, k) != maxLength(arr, k)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

    /**
     * 类似滑动窗口的解法
     *
     *   O(N)时间复杂度
     *
     */
    public int maxLengthAwesome(int[] arr, int k) {
        if(arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        // minSums[i] 表示以i位置开头可以计算到的最小的累加和
        int[] minSums = new int[N];
        // minSumEnds[i] 表示以i位置开头可以计算到的最小累加和的最右边界的位置时哪里
        int[] minSumEnds = new int[N];
        minSums[N - 1] = arr[N - 1];
        minSumEnds[N - 1] = N - 1;
        // 初始化数组(类似动态规划)
        for (int i = N - 2; i >= 0; i--) {
            // 判断i+1的位置是否小于0，只有小于0，那么继续向右扩才可以得到最小值，否则正数的话，是无法获取到最小值的
            if(minSums[i + 1] < 0) {
                minSums[i] = arr[i] + minSums[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            } else {
                minSums[i] = arr[i];
                minSumEnds[i] = i;
            }
        }

        // 迟迟扩不进来的那一块的开头的位置，就是当前向右扩的不能包含的位置，加上该位置就大于K了
        int end = 0;
        int sum = 0;
        int ans = 0;

        for (int i = 0; i < arr.length; i++) {
            // while循环结束之后：
            // (1) 如果以i开头的情况下，累加和<=k的最长子数组是arr[i..end-1]，看看这个子数组的长度能不能更新res
            // (2) 如果以i开头的情况下，累加和<=k的最长子数组比arr[i..end-1]短，更新还是不更新res都不会影响最终结果
            while (end < arr.length && sum + minSums[end] <= k) {
                // 向右扩张，每次都加上当前位置的向右的最小累加和，然后end也移动到最小累加和的最右边界，因为是最小累加和
                // 那么就可以计算出来的长度是最长的长度
                sum += minSums[end];
                end = minSumEnds[end] + 1;
            }
            ans = Math.max(ans, end - i);
            // 因为minSums[i]可能表示的是一段范围的最小累加和，所以还需要计算这段范围中每个数是否可以继续向右累加可以获取到最长的长度
            // 那么就需要将前面的数依次减去，顺序让每个数依次向后尝试累加end位置的最小累加和是否<=k，以及计算最长的长度
            if(end > i) { // 还有窗口，就算窗口没有数字[i~end)[2,2)
                sum -= arr[i]; // 如果每个数字都计算一遍，那么到这里最终sum等于0，相当于从end=i+1的位置开始重新计算
            } else { // i == end，即将i++，所以窗口就维持不住了，所以需要将end也++
                end = i + 1;
            }
        }

        return ans;
    }



    public static int maxLength(int[] arr, int k) {
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    public static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }


    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }

}
