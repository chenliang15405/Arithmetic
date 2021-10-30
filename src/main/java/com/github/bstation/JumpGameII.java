package com.github.bstation;

/**
 * @author tangsong
 * @date 2021/5/9 19:33
 */
public class JumpGameII {

    /**
     * 贪心算法
     * 给定一个整数数组arr
     *   arr[i] = j 表示从i位置开始，跳一步可以往右越过随意的1-j个位置，你一开始站在0位置，请问跳到N-1个位置，最少需要几步？
     */


    /**
     * 记录每一次的步数可以跳的最大边界，并且在向最大边界移动的过程中需要记录当前移动的过程中可以跳的最大边界，为下一步做准备，
     * 并不一定非要到最大边界才跳下一步，只不过是在这个过程中找到下一步的最大边界，这样就可以找到最少的步数，跳的最远的边界
     *
     */
    private int jumpGame(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int jump = 0; // 一共需要跳的步数
        int cur = 0; // 当前步数能够跳的最大的边界
        int next = 0; // 最大边界
        for (int i = 0; i < arr.length; i++) {
            // i 代表的是步数，arr[i]代表这个位置可以获取的步数
            if(cur < i) {
                jump++;
                cur = next;
            }
            next = Math.max(next, i + arr[i]); // 比较当前记录的最大边界和走步数的过程中经过的位置获取到的最大边界 哪个大
        }
        return jump;
    }


}
