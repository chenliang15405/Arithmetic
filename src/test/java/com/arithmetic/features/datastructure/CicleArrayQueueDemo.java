package com.arithmetic.features.datastructure;

/**
 * @author alan.chen
 * @date 2020/5/7 10:25 AM
 */
public class CicleArrayQueueDemo {

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(3);

        queue.addQueue(1);
        queue.addQueue(2);
        queue.addQueue(3);

        System.out.println(queue.peek());

        System.out.println(queue.popQueue());

        queue.listQueue();

        System.out.println(queue.peek());
    }

}


class ArrayQueue {
    private int maxSize;
    private int rear;
    private int front;
    private int[] array;

    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize + 1;
        rear = 0;
        front = 0;
        // 使用了rear指向后一个元素的位置，所以预留了一个空间，如果是实际创建的数组需要多一个才是实际的大小
        array = new int[maxSize + 1];
    }

    public boolean isFull() {
        // 因为是环形队列，所以rear可能会小于front
        return (rear + 1) % maxSize == front;
    }

    public boolean isEmpty() {
        return rear == front;
    }

    public void addQueue(int num) {
        if(isFull()) {
            System.out.println("队列已满");
            return;
        }
        // 因为rear就是指向队列最后一个元素的后一个位置，直接赋值
        array[rear] = num;
        // 将rear后移，因为是环形对列，需要重复所以使用取模
        rear = (rear + 1) % maxSize;
    }

    public int popQueue() {
        if(isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        // 因为front指向当前元素，所以直接取出
        // 将front后移，因为是环形队列，所以front需要重复使用
        int value = array[front];
        front = (front + 1) % maxSize;
        return value;
    }

    public void listQueue() {
        if(isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        // 应该是从front开始遍历，遍历有效数据个元素，因为是环形队列

        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i%maxSize, array[i%maxSize]);
        }
    }

    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    public int peek() {
        if(isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        // 队列的头数据
        return array[front];
    }


}
