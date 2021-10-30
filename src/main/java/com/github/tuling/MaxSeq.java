package com.github.tuling;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/5/3 16:26
 */
public class MaxSeq {

    /**
     * 最长连续递增数列  贪心算法
     *  计算无序数组中最长的连续递增的子序列的长度
     */

    @Test
    public void test() {
        int[] arr = new int[]{1,2,3,2,3,4,3,4,5,6,7};
        System.out.println(findMaxLength(arr));
    }


    /**
     * 遍历数组，判断元素是否递增，如果递增，那么统计长度，否则重新从当前元素开始统计长度
     *
     */
    private int findMaxLength(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int max = 0;
        int cur = 0;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] > nums[i - 1]) {
                // 当递增序列满足时，每次都计算递增序列的最大值，  最大长度的计算哪位 i - cur + 1，计算长度需要+1
                max = Math.max(max, i - cur + 1);
            } else {
                // 当前递增序列不满足时，记录cur为新的开始递增序列的起始节点
                cur = i;
            }
        }
        return max;
    }



}
