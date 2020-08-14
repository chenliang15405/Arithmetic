package com.arithmetic.features.datastructure.niukewang;

/**
 * 数组实现队列
 *
 * @author alan.chen
 * @date 2020/5/10 7:10 PM
 */
public class ArrayQueue2 {

    private int[] array;

    private int start;  // 记录队列头的位置

    private int end;    // 记录队列尾的位置

    private int size;  // 记录队列中存储的元素个数


    public ArrayQueue2(int arraySize) {
        array = new int[arraySize];
        start = 0;
        end = 0;
        size = 0;
    }

    public void push(int num) {
        if(size >= array.length) {
            throw new IllegalArgumentException("the queue is full");
        }
        size++;
        array[end] = num;
        end = end == array.length - 1 ? 0 : end + 1;
    }

    public Integer peek() {
        if(size == 0) {
            return null;
        }
        return array[start];
    }

    public int poll() {
        if(size == 0) {
            throw new IllegalArgumentException("the queue is empty");
        }
        size--;
        int cur = array[start];
        start = start == array.length - 1 ? 0 : start + 1;
        return cur;
    }


}
