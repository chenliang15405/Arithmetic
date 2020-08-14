package com.arithmetic.features.arithmetic;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author alan.chen
 * @date 2020/5/8 7:08 PM
 */
public class RandomQuickSort {

    private static int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};

    @Test
    public void randomQuickSort() {
        quicksort(array, 0, array.length - 1);

        Arrays.stream(array).forEach(System.out::println);
    }

    /**
     * 随机快排，每次取基准的时候取一个随机数和最右边的数进行交换，这样可以避免最差情况
     *
     * 时间复杂度 O(n*logN)
     */
    private void quicksort(int[] array, int L, int R) {
        if(L < R) {
            // 设置随机数为基准,并和R交换 可以避免最差情况
            swap(array, L + (int)((R - L + 1) * Math.random()), R);
            int[] p = partition(array, L, R);
            quicksort(array, L, p[0] - 1);
            quicksort(array, p[1] + 1, R);
        }
    }

    private int[] partition(int[] array, int L, int R) {
        int less = L - 1;
        int more = R;
        while(L < more) {
            if(array[L] > array[R]) {
                swap(array, L, --more);
            } else if(array[L] < array[R]) {
                swap(array, L++, ++less);
            } else {
                L++;
            }
        }
        // 将最右边的基准和more指向的数据交换，因为more比较完已经指向了要不是比R大的数，要不是R自身
        swap(array, more, R);
        return new int[]{less + 1, more};
    }

    private static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }


}
