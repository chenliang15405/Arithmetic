package com.github.bstation.array;

/**
 * @author tangsong
 * @date 2021/9/11 10:39
 */
public class Test {

    public static void main(String[] args) {

        int n = 10;

        int sum = 1;
        int i = 1;
        while(sum < n) {
            sum = sum << i;
            i++;
        }

        System.out.println(i);
    }

}
