package com.arithmetic.features.arithmetic.interview.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * 换钱的方法数
 *  给定数组arr，arr中所有的值都为正数并且不重复，每个值代表一种面值的货币，每种面值的货币都可以使用任意张，再给定一个正数aim代表要找的钱数
 *  求换钱有多少种方法
 *
 * @author tangsong
 * @date 2021/4/11 22:42
 */
public class CoinsWay {

    // 暴力递归
    public int coins1(int[] arr, int aim) {
        if(arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process1(arr, 0, aim);
    }

    /**
     * 暴力递归，计算每个货币使用的张数时有多少种方法
     *     当某个货币或某几个货币确定之后，剩下的货币计算的方法 -> 这种计算方法可能会有重复，所以可以优化，将这种确定性的剩下的货币的方法缓存起来
     *
     *  arr: 货币数组
     *  index: 可以任意自由使用index及其之后的所有钱
     *  aim: 当前的目标数
     */
    private int process1(int[] arr, int index, int aim) {
        int res = 0;
        if(index == arr.length) {
            // 当计算到最后一个货币的时候，判断当前的目前钱数是否为0，如果是0则表示一种有效方法，否则不计算为有效方法
           res += aim == 0 ? 1 : 0;
        } else {
            for (int zhang = 0; arr[index] * zhang < aim; zhang++) {
                // 当index时的货币选择zhang的个数之后，使用其他的货币可以计算出来剩余aim目标数的方法有多少种，并进行累加
                res += process1(arr, index + 1, aim - arr[index] * zhang);
            }
        }
        return res;
    }


    // 记忆优化

    private Map<String, Integer> map = new HashMap<>();
    /**
     * 记忆优化的原理是：当指定index和aim确定的时候，那么无论index之前的钱使用的方法是多少，指定index和指定aim的组合的多少的种类个数是确定的，
     * 所以，在暴力递归的过程中，会有大量的重复计算的过程，可以使用缓存的方式来进行优化，记录index和aim确定的时候可以计算得到的组合种类数，直接从缓存中获取
     *
     * 使用map作为缓存：
     *  key: "index_aim"
     *  value: 组合的种类个数
     *
     */
    private int process2(int[] arr, int index, int aim) {
        int res = 0;
        if(index == arr.length) {
            res += aim == 0 ? 1 : 0;
        } else {
            for (int i = 0; arr[index] * i < aim; i++) {
                int nextAim = aim - arr[index] * i;
                // 确定唯一的key, 因为一个index和aim可以确定唯一的标识，得到固定的组合数量，这里的计算的是index, 并且需要计算index+1的组合数量，所以key是由index+1拼接
                String key = String.valueOf(index + 1) + "_" + String.valueOf(nextAim);
                if(map.containsKey(key)) {
                    res += map.get(key);
                } else {
                    res += process2(arr, index + 1, nextAim);
                }
            }
        }
        // 在计算到每个index和aim的确定组合的数量时，记录到map中
        map.put(String.valueOf(index) + "_" + String.valueOf(aim), res);
        return res;
    }

    // 动态规划

}
