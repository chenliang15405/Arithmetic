package com.arithmetic.features.datastructure.niukewang;


/**
 * 数组实现队列
 *
 * @author alan.chen
 * @date 2020/5/10 7:10 PM
 */
public class ArrayQueue {

    private int[] array;

    private int start;  // 记录队列头的位置

    private int end;    // 记录队列尾的位置

    private int size;  // 记录队列中存储的元素个数


    public ArrayQueue(int arraySize) {
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
        if(end + 1 >array.length) {
            end = 0;
        }
        array[end++] = num;
    }

    public int peek() {
        if(size == 0) {
            throw new IllegalArgumentException("the queue is empty");
        }
        return array[start];
    }

    public int poll() {
        if(size == 0) {
            throw new IllegalArgumentException("the queue is empty");
        }
        if(start + 1 > array.length) {
            start = 0;
        }
        size--;
        return array[start++];
    }


}
