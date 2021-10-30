package com.github.左神算法.Manacher;

import org.junit.Test;

/**
 * @author tangsong
 * @date 2021/8/7 19:23
 */
public class Manacher {

    /**
     * 找到一个字符串中的最长回文子串，返回这个最长回文子串的长度是多少
     *
     *  abc12321 的最长回文子串长度是5
     *
     *  Manacher算法思想：
     *  增加当前回文子串的最右边界R
     *   1. i在R外，则只能暴力扩，向左右两边寻找是否可以找到最长的回文子串
     *   2. i在R内：
     *      1> i`(i关于C的对称的位置)在LR内，O(1), i`的回文长度就是i的回文长度
     *      2> i`不完全在LR内，L的部分已经超过C的L范围，那么O(1)获取，i的回文半径就是R-i
     *      3> i`的回文长度的左边位置压线L，那么i的回文长度最少和i`相同，还需要比较R+1和i回文半径左边的数是否相等，继续暴力扩查看是否可以扩的更多
     *
     */

    @Test
    public void test() {
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right(str)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }


    /**
     * Manacher算法 O(N)
     *   1. 为了兼容奇数字符串长度和偶数字符串长度问题，将字符串中加入特殊的字符（任何字符都可以，不影响字符串的比较，因为加入的特殊字符只会和特殊字符比较）
     *   2. 回文半径数组，统计每个位置的回文半径，并且可以直接获取到已统计过位置的回文半径，不用再次重新统计
     *   3. 最右扩充边界，表示当前最大的回文子串的最有边界的位置，如果当前统计的元素的位置已经在R内，那么可以通过O(1)时间复杂度找到回文子串长度
     *   4. 每次统计完成每个元素的回文子串的长度，查看是否可以更新R,R是不回退的，所以可以提高效率
     *
     */
    public int manacher(String s) {
        if(s == null || s.length() < 0) {
            return 0;
        }
        // 12321 -> #1#2#3#2#1#
        char[] str = manacherString(s);
        // 回文半径数组
        int[] pArr = new int[str.length];
        // 当前统计的回文串的中心
        int C = -1;
        // 讲述中：R代表最右的扩成功的位置
        // coding：最右的扩成功位置的，再下一个位置
        int R = -1;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < str.length; i++) {
            // 因为R表示的扩成功位置的下一个位置，所以这里必须>，不能=
            // 如果R>i，则表示i在已经扩充的回文边界中，那么有3中可能，但是最少的回文半径长度就是R-i或者对称点i`已经统计的回文半径长度
            // 如果 R<=i，那么表示当前位置还没有在R中，那么就需要暴力扩
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;

            // 这里考试比较当前元素的左右两边是否还可以扩展
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if(str[i + pArr[i]] == str[i - pArr[i]]) {
                    // 如果已经统计了回文半径的左右边还有元素相等，那么就更新回文半径
                    pArr[i]++;
                } else {
                    // 否则，直接break,指针i在R内，并且i`没有超过或者不完全超过C的回文边界，那么其实是直接走到这里
                    break;
                }
            }
            // 判断当前元素的回文有边界是否超过了R
            if(i + pArr[i] > R) {
                // 更新R和C
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }

        // 这里的max是回文半径，但是回文半径是manacherString的回文半径，-1就是原始字符串的回文子串的长度
        return max - 1;
    }

    private char[] manacherString(String str) {
        char[] chars = str.toCharArray();
        char[] ans = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            // 偶数位置需要填充#
            ans[i] = (i & 1) == 0 ? '#' : chars[index++];
        }
        return ans;
    }




    // for test
    public int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

}
