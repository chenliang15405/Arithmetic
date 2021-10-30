package com.github.bstation.other;

import org.junit.Test;

import java.util.*;

/**
 * @author tangsong
 * @date 2021/6/6 19:33
 */
public class FindKMajor {
    private int N;

    /**
     *  给定一个长度为N的数组arr，和一个大于1的正数K, 如果有那些数出现次数大于N/K，就返回这些数
     *
     *  要求：
     *    时间复杂度O(N)，额外空间复杂度O(K)
     *
     *  解法：
     *   1. 因为出现的次数大于N/K, 那么一共肯定最多只能有K-1个数符合要求（因为 K * N/K > N, 但是数组的长度最大就是N, 不可能出现>N）
     *   2. 将每个数的个数统计起来，如果记录表中的统计个数已经为K-1个了，还有不在表中的整数，就对所有的数的数量-1
     *   （为什么需要-1，因为可能新的数的次数较大，将出现次数少的数从统计表中删除）
     *   3. 根据记录表中的记录的数据统计每个整数真实的出现次数，统计>N/K的整数并返回列表
     *
     */

    @Test
    public void test() {
        int[] arr = {1,8,6,1,5,8,4,1,3};
        int K = 5;

        System.out.println(kMajor(arr, K));

    }

    /**
     * 关键思路：最后的结果数量一定少于等于K-1个，
     *          维护一个记录表，当没有超过K-1个，则加入记录表，超过则全部次数减1，为0的则删除
     *          最后计算每个元素的出现次数是否>N/K
     *
     */
    public List<Integer> kMajor(int[] arr, int K) {
        List<Integer> list = new ArrayList<>();
        if(arr == null || arr.length <= 0) {
            return list;
        }
        int N = arr.length;
        // 使用map记录当前的数字和对应的次数, 记录次数出现最多的前K-1个数
        // 这里的map只是用来做筛选出现次数最多的前K-1个数
        Map<Integer, Integer> map = new HashMap<>();
        // 使用这个map，记录每个数的出现次数
        Map<Integer, Integer> times = new HashMap<>();
        for (int i = 0; i < N; i++) {
            if(map.containsKey(arr[i])) {
                map.put(arr[i], map.get(arr[i]) + 1);
            } else {
                // 判断当前map中数字的个数是否 >= K - 1个，因为最多只有这么多数，
                // 所以如果大于这么多数，就需要对所有的数的次数 -1,看能不能淘汰部分数
                if(map.size() >= K - 1) {
                    decrNumTimes(map);
                } else {
                    map.put(arr[i], 1);
                }
            }

            // 记录每个数出现的次数
            times.put(arr[i], times.getOrDefault(arr[i], 0) + 1);
        }
        // 遍历map，判断总的次数是否大于N/K
        map.forEach((key, value) -> {
            if (times.get(key) > N / K) {
                list.add(key);
            }
        });
        return list;
    }

    /**
     * 对所有的key对应的次数减1
     */
    private void decrNumTimes(Map<Integer, Integer> map) {
        // 存储需要删除的key
        List<Integer> list = new ArrayList<>();
        map.forEach((key, value) -> {
            // 如果次数为0，则需要从map中删除，因为说明有相同的次数出现的key
            if(value - 1 == 0) {
                list.add(key);
            }
            map.put(key, value - 1);
        });
        // 删除次数为0的key
        for (Integer key : list) {
            map.remove(key);
        }
    }


}
