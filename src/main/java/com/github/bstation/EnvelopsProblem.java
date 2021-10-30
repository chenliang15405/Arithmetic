package com.github.bstation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @author tangsong
 * @date 2021/5/30 15:22
 */
public class EnvelopsProblem {

    /**
     * 给定一批信封，每个信封都有长和宽，当A信封的宽和高都小于B信封时，则A信封可以被装入到B信封中，
     * 那么一批信封中，总共有多少个信封可以嵌套
     *
     */

    public static class Envelope {
        public int l; // 长
        public int h; // 高

        public Envelope(int l, int h) {
            this.l = l;
            this.h = h;
        }
    }


    /**
     * 构建数组，利用最大子序列获取到最多的个数，因为按照长已经排序了，那么后面的都比前面的长度高，如果长度相同
     * 那么按照高度进行降序排列，那么最高的高度之后是不可能放入到前面的信封的，因为高度小，所以只需要获取到指定顺序数组的最长子序列即可
     *
     */
    public int maxEnvelopes(int[][] matrix) {
        // 先将信封进行排序
        Envelope[] envelopes = getSortedEnvelops(matrix);
        // 构建ends数组，ends数组中保存的是信封的第二维度的值，也就是高，然后找到这个ends数组中的最长递增子序列
        int[] ends = new int[matrix.length];
        ends[0] = envelopes[0].h; // 第一个值就是信封的第一个值的高度
        int right = 0;
        int l = 0;
        int r = 0;
        int m = 0;
        for (int i = 1; i < envelopes.length; i++) {
            // 开始二分查找，每个信封的高度应该放在某个位置，保证ends数组的有序性，
            l = 0;
            r = right;
            while (l <= r) {
                m = (l + r) / 2;
                if(envelopes[i].h > ends[m]) {
                    l = m + 1;
                } else {
                    l = m - 1;
                }
            }

            right = Math.max(right, l);
            ends[l] = envelopes[i].h;
        }
        return right + 1;
    }

    /**
     * 对信封数据进行排序，按照第一维度进行从小到大的排序，如果第一维度相等，那么就按照第二维度从大到小进行排序
     *
     */
    private Envelope[] getSortedEnvelops(int[][] matrix) {
        Envelope[] envelopes = new Envelope[matrix.length];
        // 构建为对象
        for (int i = 0; i < matrix.length; i++) {
            envelopes[i] = new Envelope(matrix[i][0], matrix[i][1]);
        }
        // 利用比较器进行排序
        // 判断当前的长度是否相等，如果相等，那么就按照高度降序排序，如果不相等，则按照长度升序排列
        Arrays.sort(envelopes, (o1, o2) -> o1.l == o2.l ? o2.h - o1.h : o1.l - o2.l);

        return envelopes;
    }


}
