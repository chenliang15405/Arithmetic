package com.arithmetic.features.arithmetic;

import org.junit.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author alan.chen
 * @date 2020/5/10 10:27 AM
 */
public class HeapSort {
    /**
     * 堆的数据结构是使用数组来表示，逻辑结构为完全二叉树
     *
     * 二叉树：一个节点有子孩子的结构
     *
     * 完全二叉树：每个节点都有完整的子孩子或者 子孩子不全的情况下，叶子节点是从左向右填充的
     *
     * 二叉树的每个节点： （数组中i表示节点的索引）左孩子的位置：i*2+1，右孩子：i*2+2
     *                  父节点下标：(i-1)/2
     *
     * 大根堆：任何一棵子树的最大值就是这个子树的头部就叫做大根堆，整个树的最大值是整个树的头部
     * 小根堆：任何一棵子树的最小值就是这个子树的头部就叫做小根堆，整个树的最小值是整个树的头部
     */

    private static int[] array = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};

    @Test
    public void heapSortTest() {
        heapSort(array);
    }

    // 堆排序
    private void heapSort(int[] array) {
        if(array == null || array.length < 2) {
            return;
        }
        // 调整数组为大根堆逻辑
        for (int i = 0; i < array.length; i++) {
            heapInsert(array, i);
        }
        // 根据大根堆进行排序, 每次排序是将大根堆的头部和最后一个值进行交换，这样就排序一个最大值到最后，
        // 然后再heapify（调整大根堆），然后再重复，就会得到有序的数据（asc）
        int heapSize = array.length; // 设置需要排序的数组范围
        swap(array, 0, --heapSize);
        while (heapSize > 0) {
            heapify(array, 0, heapSize);
            swap(array, 0, --heapSize);
        }

        Arrays.stream(array).forEach(System.out::println);
    }

    private void heapify(int[] array, int index, int heapSize) {
        while(true) {
            int left = index * 2 + 1;  // top节点的左孩子
            if(left >= heapSize) {
                break;
            }
            // left + 1表示右孩子
            // 这个 array[left + 1] > array[left]顺序不能写反，因为还需要保证前面的left + 1 < heapSize
            int largest = left + 1 < heapSize && array[left + 1] > array[left] ? left+1 : left;

            largest = array[index] > array[largest] ? index : largest;

            if(largest == index) {
                break;
            }
            // 如果largest和index不想等，则说明需要调整头节点的位置和子节点中最大的交换
            swap(array, largest, index);
            index = largest; // 将index始终指向从尾部调整到顶部的元素的位置，进行比较，因为之前就已经是大根堆，现在打乱了，需要重新调整
        }
        //int left = index * 2 + 1;
        //while (left < heapSize) {
        //    //int largest = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
        //    int largest;
        //    if(left + 1 < heapSize && array[left+1] > array[left]) {
        //        largest = left + 1;
        //    } else {
        //        largest = left;
        //    }
        //    // 这个 array[left + 1] > array[left]顺序不能写反，因为还需要保证前面的left + 1 < heapSize
        //    //int largest = left + 1 < heapSize && array[left + 1] > array[left] ? left+1 : left+1;
        //    largest = array[index] > array[largest] ? index : largest;
        //
        //    if(largest == index) {
        //        break;
        //    }
        //    // 如果largest和index不想等，则说明需要调整头节点的位置和子节点中最大的交换
        //    swap(array, largest, index);
        //    index = largest;
        //    left = index * 2 + 1;
        //}

    }

    // 调整数组逻辑结构为大根堆
    private void heapInsert(int[] array, int index) {
        // 因为使用了方法参数来作为一个新的变量，如果直接在for循环中调整，则需要使用一个新的变量作为i的指向
        while (array[index] > array[(index-1) / 2]) {
            swap(array, index, (index-1) / 2);
            index = (index-1) / 2;
        }
    }

    private void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    @Test
    public void test2() {
        //heapSort2(array);

        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> {
           return a - b;
        });

        for (int i = 0; i < array.length; i++) {
            heap.add(array[i]);
        }
        int size = heap.size();
        for (int i = 0; i < size; i++) {
            System.out.println(heap.poll());
        }
    }

    private void heapSort2(int[] array) {
        for (int i = 0; i < array.length; i++) {
            heapInsert2(array, i);
        }
        int heapSize = array.length;
        while(heapSize > 0) {
            swap(array, 0, --heapSize);
            heapify2(array, 0, heapSize);
        }

    }

    private void heapify2(int[] array, int i, int heapSize) {
        int left = i * 2 + 1;
        while(left < heapSize) {
            int right = i * 2 + 2;
            int largest = right < heapSize && array[right] > array[left] ? right : left;
            largest = array[largest] > array[i] ? largest : i;

            if(largest == i) {
                break;
            }
            swap(array, largest, i);
            i = largest;
            left = i * 2 + 1;
        }
    }

    private void heapInsert2(int[] array, int index) {
        while(array[index] > array[(index - 1) / 2]) {
            swap(array, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }



    @Test
    public void test3() {
        heapSort3(array);

        Arrays.stream(array).forEach(System.out::println);
    }

    private void heapSort3(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int index = i;
            while(array[index] > array[(index - 1) / 2]) {
                swap(array, index, (index -1 ) / 2);
                index = (index - 1) / 2;
            }
        }

        int heapSize = array.length;
        while (heapSize > 0) {
            swap(array, 0, --heapSize);
            heapify3(array, 0, heapSize);
        }

    }

    private void heapify3(int[] array, int index, int heapSize) {
        int left = index * 2 + 1;
        while(left < heapSize) {
            int largest = left + 1 < heapSize && array[left + 1] > array[left] ? left + 1 : left;
            largest = array[largest] > array[index] ? largest : index;
            if(largest == index) {
                break;
            }
            swap(array, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }

}
