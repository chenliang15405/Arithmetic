package com.github.interview;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author tangsong
 * @date 2021/9/27 17:50
 */
public class NumsK {

    /**
     * num1和nums2都是升序的数组，需要k对最小的和(u1, v1)
     * 例如[1,7,9] 和 [1,2,4]  k= 3，那么输出(1, 1) (1, 2) (1, 4) ，
     * 例如[1,1,4]和[1,2,5] ，输出(1,1) (1,1) (1,2)
     *
     */
    @Test
    public void test() {
//        int[] nums1 = {1, 7, 9};
//        int[] nums1 = {1, 7, 9};
        int[] nums1 = {1, 1, 4};
        int[] nums2 = {1, 2, 5};
        int k = 3;

        List<List<Integer>> list = solution(nums1, nums2, k);

        System.out.println(list);
    }


    public List<List<Integer>> solution(int[] nums1, int[] nums2, int k) {
        if(nums1 == null || nums2 == null || k <= 0) return null;

        int n = nums1.length;
        int m = nums2.length;
        List<List<Integer>> ans = new ArrayList<>();
        int l1 = 0;
        int l2 = 0;

        for (int i = 0; i < k; i++) {
            List<Integer> list = new ArrayList<>();
            int first = nums1[l1] < nums2[l2] ? nums1[l1] : nums2[l2];
            int second = nums1[l1] == first ? nums2[l2] : nums1[l1];
            list.add(first);
            list.add(second);
            ans.add(list);
            if(l1 + 1 < n && l2 + 1 < m && nums1[l1 + 1] < nums2[l2 + 1]) {
                l1++;
            } else {
                l2++;
            }
        }

        return ans;
    }

}
