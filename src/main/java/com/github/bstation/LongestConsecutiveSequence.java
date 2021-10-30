package com.github.bstation;

import sun.security.util.Length;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/5/9 20:43
 */
public class LongestConsecutiveSequence {

    /**
     * 最长递增子序列
     *
     *  给定一个数组arr，可以自由选择数字组成序列
     *  如果某个序列从最小值开始到最大值的每一个整数都在序列中出现，称这个序列是是连续递增序列
     *
     *  返回数组arr中可以生成的最长的递增子序列的长度
     *
     * https://leetcode-cn.com/problems/longest-increasing-subsequence/
     */


    /**
     *  使用Map进行操作，key -> 当前出现的数字， value -> 当前组成连续序列的长度
     *  Map的作用：
     *    1. 充当set，过滤重复的字符，防止统计数字长度出错
     *    2. 记录某个数字开头或结尾的连续序列的长度
     *
     *
     */
    public int maxLength(int[] arr) {
        if(arr == null || arr.length == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();

        int max = 0;
        for (int num : arr) {
            // 判断当前map中是否已经有该元素，防止统计连续序列时出错
            if(!map.containsKey(num)) {
                // 将当前元素加入到map中，并且默认的序列的长度是1，例如：3，那么就表示 3-3:1 以3开头和以3结尾的序列，并且长度是1
                map.put(num, 1);
                // 判断map中是否已经有当前数的前一个数字，如果有则取出该数字目前记录的连续序列的长度，如果没有则表示map中没有该数字，序列的长度为0
                int preLength = map.containsKey(num - 1) ? map.get(num - 1) : 0;
                // 判断map中是否有已经有当前数的后一个数字
                int postLength = map.containsKey(num + 1) ? map.get(num + 1) : 0;
                // 计算当前序列的新的长度，因为如果能取到preLength、postLength，则表示当前的序列又新增一个当前元素，所以这个序列的开头元素和结尾元素记录当前序列的总长度即可
                int allLength = preLength + postLength + 1;

                // 获取这个序列的第一个元素和最后一个元素, 只需要序列的开头元素和结尾元素记录序列的长度即可，中间的元素已经没有用了
                // first 表示序列的第一个元素
                int first = num - preLength; // 应该这么写：num - 1 - preLength + 1 = num - preLength
                // tail 表示序列的最后一个元素
                int tail = num + postLength; // 应该这么写； num + 1 + length - 1 = num + postLength

                map.put(first, allLength);
                map.put(tail, allLength);

                // 记录每次的序列最大长度
                max = Math.max(max, allLength);
            }
        }

        return max;
    }

    /**
     * 动态规划
     *
     */
    public int dp(int[] arr) {
        if(arr == null || arr.length <= 0) {
            return -1;
        }
        int n = arr.length;
        // dp数组，dp[i]表示，第i个位置统计的最长的递增子序列
        int[] dp = new int[n];
        // 默认0位置的递增子序列为1
        dp[0] = 1;
        int maxLen = dp[0];

        for(int i = 1; i < arr.length; i++) {
            // 每行默认最长的长度初始为1，就是自身
            dp[i] = 1;
            // 判断当前的元素是否大于前面的每个元素，如果大于，则重新计算当前的最长子序列，子序列可以不连续
            for(int j = 0; j < i; j++) {
                if(arr[i] > arr[j]) {
                    // 当前位置的最长递增子序列的长度就是 当前位置的长度和 当前位置+之前元素组成的子序列的长度比较，dp[j]+1 表示前面的元素+当前元素组成的子序列的长度
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            // 对每个位置组成的子序列比较
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }


    /**
     * 二分查找计算最长递增子序列
     *
     */
    public int maxLengthWithBS(int[] arr) {
        int n = arr.length;
        int[] nums = new int[n];
        int len = 0;
        nums[len] = arr[0];

        for(int i = 1; i < n; i++) {
            if(nums[len] > arr[i]) {
                nums[++len] = arr[i];
            } else {
                // 二分查找，找到小于当前元素的第一个元素
                int left = 0;
                int right = len;
                int pos = -1; // 设置为-1，如果找不到比当前元素小的，则设置为0索引的位置
                while (left <= right) {
                    int mid = left + ((right - left) >> 1);
                    if(nums[mid] < arr[i]) {
                        pos = mid;
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
                // 替换该位置的元素，保证可以重新开始统计新的递增序列，并且原有的递增序列的长度不变
                nums[pos + 1] = arr[i];
            }
        }
        // len 是索引
        return len + 1;
    }


}
