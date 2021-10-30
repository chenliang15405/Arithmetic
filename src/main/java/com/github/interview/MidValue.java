package com.github.interview;

import org.junit.Test;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author tangsong
 * @date 2021/10/13 12:01
 */
public class MidValue {

    /**
     * 在数组中找到左边数都比它小, 右边数都比它大的所有数
     *
     */

    @Test
    public void test() {
        // 1 10
        int[] arr = {1, 2,3,4,9,3,2,10};

        System.out.println(Arrays.toString(midValue(arr)));
    }

    private static int[] midValue(int[] nums){
        if(0 == nums.length) {return null;}
        // 单调栈 存储当前满足条件的值
        Stack<Integer> midNumsStack = new Stack<>();
        // 记录当前遍历过的最大值(新入栈的数必须不小于max)
        int max = nums[0];
        midNumsStack.push(nums[0]);
        for(int i = 1; i < nums.length; i++){
            // 先检查新值num是否小于栈内元素, 否则栈内元素不满足条件
            while(!midNumsStack.empty() && nums[i] <= midNumsStack.peek()){
                midNumsStack.pop();
            }
            if(nums[i] > max){
                midNumsStack.push(nums[i]);
                max = nums[i];
            }
        }

        int[] result = new int[midNumsStack.size()];
        int i = 0;
        while(midNumsStack.size() > 0){
            result[i++] = midNumsStack.pop();
        }

        return result;
    }


}
