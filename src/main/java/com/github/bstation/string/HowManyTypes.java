package com.github.bstation.string;

import java.util.HashSet;
import java.util.Set;

/**
 * @author tangsong
 * @date 2021/6/6 11:15
 */
public class HowManyTypes {

    /**
     * 如果两个字符串，所含字符种类完全一样，就算做一类，只由小写字母（a~z）组成的一批字符串，
     * 都放在字符类型的数组String[] arr中，返回arr中一共有多少类
     *
     *
     *  两种解法：
     *    1. 使用boolean[]数组统计，并还原为字符串，加入到去重的列表中
     *    2. 使用位运算的方式，优化常数项的时间复杂度
     *
     *  &（与运算）：两个相同才是1
     *  |（或运算） ： 有一个为1就是1 （相当于 +，10 | 5 = 15）每个二进制位上的数相加
     *  ^（异或） ： 两个不同才是1
     *
     */


    /**
     * 暴力解法
     *
     */
    public int types1(String[] arr) {
        // 创建HashSet，主要是为了保存最后的种类数，并通过set特点去重
        Set<String> types = new HashSet<>();
        // 遍历数组
        for (String str : arr) {
            // 创建boolean数组，统计当前字符串中的字符种类
            // kinds[0] 代表a, kinds[1]代表b kinds[2] 代表c
             boolean[] kinds = new boolean[26];// 最多只有26个字母
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                // 将指定的元素位置设置为true，相当于将一个字符串进行去重操作统计多少个不同的字符
                kinds[chars[i] - 'a'] = true;
            }
            // 将字符串还原
            String keys = "";
            for (int i = 0; i < 26; i++) {
                if(kinds[i]) {
                    keys += String.valueOf((char)(i +'a'));
                }
            }
            types.add(keys);
        }
        return types.size();
    }


    /**
     * 位运算
     *    通过位运算减少常数项的时间复杂度
     *
     */
    public int types2(String[] arr) {
        Set<Integer> types = new HashSet<>();
        for (String str : arr) {
            int key = 0; // 使用int类型作为一个整数，记录当前的字符串对应的位  00000000000
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                // 将当前的数字作为移位，将1向左移动多少位，那么int的字节位就会改变， 移动2位 ->  000000100 使用不同的位来表示不同种类的字符串
                key |= (1 << (chars[i] - 'a'));
            }
            types.add(key);
        }
        return types.size();
    }

}
