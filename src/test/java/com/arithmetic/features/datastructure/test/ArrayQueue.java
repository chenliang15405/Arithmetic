package com.arithmetic.features.datastructure.test;

/**
 * @author alan.chen
 * @date 2020/5/19 11:52 AM
 */
public class ArrayQueue {

    private int maxSize;

    private int start;

    private int end;

    private int[] array;

    public ArrayQueue(int maxSize) {
        this.array = new int[maxSize];
        this.start = 0;
        this.end = 0;
        this.maxSize = 0;
    }

    public void push(int num) {
        if(maxSize == array.length) {
            throw new RuntimeException("the queue is full!");
        }
        array[end] = num;
        if(end + 1 == array.length) {
            end = 0;
        } else {
            end++;
        }
        maxSize++;
    }

    public int pop() {
        if(maxSize == 0) {
            throw new RuntimeException("the queue is empty!");
        }
        int temp = array[start];
        if(start + 1 == array.length) {
            start = 0;
        } else {
            start++;
        }
        maxSize--;
        return temp;
    }

    public Integer peek() {
        if(maxSize == 0) {
            return null;
        }
        return array[start];
    }

}
