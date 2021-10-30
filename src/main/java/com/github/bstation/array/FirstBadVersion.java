package com.github.bstation.array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tangsong
 * @date 2021/6/13 10:58
 */
public class FirstBadVersion {

    /**

     * 假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
     *
     * 你可以通过调用 bool isBadVersion(version) 接口来判断版本号 version 是否在单元测试中出错。实现一个函数来查找第一个错误的版本。
     * 你应该尽量减少对调用 API 的次数
     *
     */

    @Test
    public void test() {
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        System.out.println(firstBadVersion(arr));
        System.out.println(firstBadVersion2(arr));
        System.out.println(firstBadVersion3(arr));
    }


    /**
     * 二分查找法 - 找到指定版本
     *
     */
    public int firstBadVersion(int[] arr) {
        int L = 0;
        int R = arr.length;
        while (L < R) {
            int mid = L + (R - L) / 2;
            if(isBadVersion(mid)) {
                // 找到最左边的一个version
                int result = mid;
                for (int i = mid - 1; i >= L; i--) {
                    if(isBadVersion(i)) {
                        result = i;
                    } else {
                        break;
                    }
                }
                return result;
            } else {
                L = mid + 1;
            }
        }
        return arr[L];
    }

    public int firstBadVersion2(int[] arr) {
        int L = 0;
        int R = arr.length;
        int result = -1;
        while (L < R) {
            int mid = L + (R - L) / 2;
            if(isBadVersion(mid)) {
                // 找到最左边的一个version
                result = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return result;
    }


    public int firstBadVersion3(int[] arr) {
        return binarySearch(arr, 0, arr.length, -1);
    }

    /**
     * 递归二分
     *
     */
    public int binarySearch(int[] arr, int L, int R, int result) {
        if(L >= R) {
            return result;
        }
        int mid = L + (R - L) / 2;

        if(isBadVersion(mid)) {
            result = mid;
            result = binarySearch(arr, L, mid - 1, result);
        } else {
            result = binarySearch(arr, mid + 1, R, result);
        }
        return result;
    }


    boolean isBadVersion(int version) {
        List<Integer> list = new ArrayList<Integer>(){
            {
                add(6);
                add(7);
                add(8);
                add(9);
                add(10);
            }
        };
        return list.contains(version);
    }

}
