package com.github.tuling;

/**
 * @author tangsong
 * @date 2021/5/2 16:04
 */
public class SqrtX {

    /**
     * 计算X平方根的整数位
     */

    /**
     * 二分法，找到最后一个数的平方小于等于需要找的数
     */
    public int sqrtX(int x) {
        if(x < 0) {
            return -1;
        }
        int index = 0;
        int l = 0;
        int r = x;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if(mid * mid <= x) {
                index = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return index;
    }


}
