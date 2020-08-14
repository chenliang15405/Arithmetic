package com.arithmetic.features.datastructure.test;

/**
 * @author alan.chen
 * @date 2020/5/19 1:39 PM
 */
public class ArrayStack {

    private int[] array;

    private int current;

    public ArrayStack(int size) {
        array = new int[size];
        current = 0;
    }

    public void push(int num) {
        if(current == array.length) {
            throw new RuntimeException("the stack is full!");
        }
        array[current++] = num;
    }

    public Integer peek() {
        if(current < 0) {
            return null;
        }
        return array[current - 1];
    }

    public int pop() {
        if(current == 0) {
            throw new RuntimeException("the stack is empty!");
        }
        return array[--current];
    }



}
