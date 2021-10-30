package com.github.interview;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tangsong
 * @date 2021/9/21 22:32
 */
public class GetK {

    /**
     *  pdd二面算法
     *
     * 给你一个数组，其中数组中的每个值与相邻元素之间的差值的绝对值是m，现在给你一个目标值k，找到数组中所有等于k的元素的索引，使用集合返回。
     * 遍历的元素越少越好，无序
     * List fun(List<Integer> list,int m,int k)
     * 就比如[1,2,3,2,1,0,-1,0,1] m=1 k=3 返回[2] ，k一定是数组中的某个值
     *
     */
    @Test
    public void test() {
        int m = 1;
        int k = 3;
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 1, 0, -1, 0, 1);

        System.out.println(fun(list, m, k));

    }


    public List<Integer> fun(List<Integer> list, int m, int k) {
        List<Integer> ans = new ArrayList<>();
        int index = 0;

        while(index < list.size()) {
            int cur = list.get(index);
            // 计算当前值和目标值之间的差，以及需要多少步可以直接到达该位置, 因为间隔是固定的，所以直接除m就可以得到步数
            int step = Math.abs(cur - k) / m;

            // 如果step=0,则表示当前位置就是K
            if(step == 0) {
                ans.add(index);
                index++;
            } else {
                // 否则，需要将当前索引直接移动到目标位置
                index += step;
            }
        }

        return ans;
    }

}
