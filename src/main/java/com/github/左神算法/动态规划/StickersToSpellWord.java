package com.github.左神算法.动态规划;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/6/24 23:30
 */
public class StickersToSpellWord {

    /**
     * 给定一个字符串str，给定一个字符串类型的数组arr，出现字符都是小写英文，arr每一个字符串代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str
     * 返回需要至少多少张贴纸可以完成这个任务
     * 例子：str = "babac" arr = {"ba", "c", "abcd"}
     * ba + ba + c = 3  abcd+abcd = 2 abcd + ba = 2
     * 所以最少需要2张
     *
     */

    /**
     * 暴力递归
     *
     */
    public int way1(String[] sticker, String target) {
        int min = process(sticker, target);
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    /**
     * 返回最少的使用的贴纸数量
     *    因为可以使用不同的贴纸进行尝试，所以一般应该是递归回溯
     */
    private int process(String[] stickers, String target) {
        // 判断当前是否已经将目标搞定
        if(target.length() == 0) {
            // 返回0，表示当前可以搞定，需要的贴纸数量在前面的过程相加，这里是下一个过程的第一个判断，这里终止了，那么表示之前的决策是正确的，并且可以满足的
            return 0;
        }
        int min = Integer.MAX_VALUE;
        // 判断每一种贴纸使用时需要的贴纸数量，计算最小的贴纸数量
        for (String sticker : stickers) {
            // 计算使用当前的贴纸是否可以搞定哪些字符
            String rest = minus(sticker, target);
            // 判断当前贴纸是否去掉了部分字符，如果一个都没有搞定，那么不要当前字符，直接下一个贴纸字符串进行尝试
            if(rest.length() != target.length()) {
                min = Math.min(min, process(stickers, rest));
            }
        }
        // 如果没有解决方案，则返回最大值
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    /**
     * 给定当前的字符串和目标字符串，返回还需要搞定的目标字符串
     */
    private String minus(String sticker, String target) {
        char[] char1 = sticker.toCharArray();
        char[] char2 = target.toCharArray();
        int[] count = new int[26];
        // 统计词频，并对词频进行相减，将剩余的词频返回为剩余的字符串
        for (int i = 0; i < char2.length; i++) {
            count[char2[i] - 'a']++;
        }
        for (int i = 0; i < char1.length; i++) {
            count[char1[i] - 'a']--;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if(count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    sb.append((char)(count[i] + 'a'));
                }
            }
        }
        return sb.toString();
    }


    /**
     * 优化2: 使用词频统计表进行统计
     *
     */
    public int way2(String[] sticker, String target) {
        // 将贴纸先记录为词频统计表，一个二维数组，每个一维数组代表一个贴纸字符串对应的词频统计
        int N = sticker.length;
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] chars = sticker[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                counts[i][chars[j] - 'a']++;
            }
        }

        int min = process2(counts, target);
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    private int process2(int[][] stickers, String target) {
        if(target.length() == 0) {
            return 0;
        }
        // 计算目标字符串的词频统计表
        char[] chars = target.toCharArray();
        int[] tcount = new int[26];
        for (int i = 0; i < chars.length; i++) {
            tcount[chars[i] - 'a']++;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            int[] sticker = stickers[i];
            // 剪枝优化，非常重要的一个优化
            // 先判断是否有包含第一个字符的，因为总是需要包含第一个字符的字符串贴纸，所以使用这种方式先优化点没有包含的
            if(sticker[chars[0] - 'a'] > 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if(tcount[j] > 0) {
                        // 计算剩余的词频
                        int num = tcount[j] - sticker[j];
                        // 拼接字符串
                        for (int k = 0; k < num; k++) {
                            sb.append((char)(j + 'a'));
                        }
                    }
                }
                String rest = sb.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }


    /**
     * 优化3 ： 使用缓存优化
     *
     */
    public int way3(String[] sticker, String target) {
        // 将贴纸先记录为词频统计表，一个二维数组，每个一维数组代表一个贴纸字符串对应的词频统计
        int N = sticker.length;
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] chars = sticker[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                counts[i][chars[j] - 'a']++;
            }
        }
        Map<String, Integer> dp = new HashMap<>();
        // 初始化条件, target.length = 0 时的贴纸数量
        dp.put("", 0);

        int min = process3(counts, target, dp);
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    private int process3(int[][] stickers, String target, Map<String, Integer> dp) {
        if(dp.containsKey(target)) {
            return dp.get(target);
        }
        // 计算目标字符串的词频统计表
        char[] chars = target.toCharArray();
        int[] tcount = new int[26];
        for (int i = 0; i < chars.length; i++) {
            tcount[chars[i] - 'a']++;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            int[] sticker = stickers[i];
            // 剪枝优化，非常重要的一个优化
            // 先判断是否有包含第一个字符的，因为总是需要包含第一个字符的字符串贴纸，所以使用这种方式先优化点没有包含的
            if(sticker[chars[0] - 'a'] > 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if(tcount[j] > 0) {
                        // 计算剩余的词频
                        int num = tcount[j] - sticker[j];
                        // 拼接字符串
                        for (int k = 0; k < num; k++) {
                            sb.append((char)(j + 'a'));
                        }
                    }
                }
                String rest = sb.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        min = min == Integer.MAX_VALUE ? -1 : min;
        dp.put(target, min);
        return min;
    }


}
