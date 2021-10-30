package com.github.bstation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/5/11 23:17
 */
public class QueryBox {

    /**
     * 今日头条
     *    数组为{3,2,2,3,1}，查询为（0,3,2）
     *    表示在数组的下标0-3的范围上，查询有多少个2
     *
     *    假设给一个数组，对这个数组的查询非常频繁，请返回所有的查询结果
     *
     */

    /**
     * 构建查询表，因为频繁查询并且需要快速返回结果，那么需要一个数据表记录数据，不要重复扫描
     *
     *  使用Map, key 是某个数组， value 是 这个数字在数组中的下标列表
     *  查询时，可以O(1)获取到下标的列表，然后对下标的列表使用二分查询
     *
     *  小技巧：
     *    在使用二分查询列表时，因为给了查询的范围区间。所以查询 <L的数据个数n1，查询<R+1的数据个数n2，然后使用n2-n1就是指定范围内的数量
     *
     */

    private Map<Integer, List<Integer>> map;


    public void queryBox(int[] arr) {
        map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if(!map.containsKey(arr[i])) {
                map.put(arr[i], new ArrayList<>());
            }
            // 将重复的元素的下标保存起来
            map.get(arr[1]).add(i);
        }
    }

    /**
     * 提供查询，在L...R范围上查询value的个数
     *
     */
    public int query(int L, int R, int value) {
        if(!map.containsKey(value)) {
            return 0;
        }
        List<Integer> list = map.get(value);
        // 进行二分查询，查询<L的数据的个数
        int num1 = countLess(list, L);
        // 查询小于R+1的个数
        int num2 = countLess(list, R + 1);

        return num2 - num1;
    }

    private int countLess(List<Integer> list, int L) {
        int left = 0;
        int right = list.size() - 1;
        int mostRight = -1;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if(list.get(mid) < L) {
                mostRight = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        // 当没有匹配到数据时， -1 + 1 = 0；
        // 当匹配到数据时， 0-7 = 8个
        return mostRight + 1;
    }


}
