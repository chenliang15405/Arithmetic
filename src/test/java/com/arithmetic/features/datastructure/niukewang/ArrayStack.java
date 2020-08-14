package com.arithmetic.features.datastructure.niukewang;

/**
 *
 * 数组实现栈
 *
 * @author alan.chen
 * @date 2020/5/10 7:14 PM
 */
public class ArrayStack {

    private int[] array;
    private int index;


    public ArrayStack(int size) {
        array = new int[size];
        index = 0;
    }

    public void push(int num) {
        if(index == array.length) {
            throw new IllegalArgumentException("the stack is full");
        }
        array[index++] = num;
    }

    public Integer peek() {
        if(index == 0) {
            return null;
        }
        return array[index - 1];
    }

    public Integer poll() {
        if(array.length == 0) {
            throw new IllegalArgumentException("the stack is empty");
        }
        return array[--index];
    }

}
