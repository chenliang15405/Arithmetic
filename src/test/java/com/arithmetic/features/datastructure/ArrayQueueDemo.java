package com.arithmetic.features.datastructure;

/**
 * 数组模拟队列
 *
 * @author alan.chen
 * @date 2020/5/4 10:26 AM
 */
public class ArrayQueueDemo {

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(3);

        queue.addQueue(1);
        queue.addQueue(2);
        queue.addQueue(3);

        System.out.println(queue.peek());

        System.out.println(queue.popQueue());

        queue.listQueue();

        System.out.println(queue.peek());

        // TODO 优化队列类型，环形队列

    }




    static class ArrayQueue {
        private int maxSize;
        private int rear;
        private int front;
        private int[] array;

        public ArrayQueue(int maxSize) {
            this.maxSize = maxSize;
            rear = -1;
            front = -1;
            array = new int[maxSize];
        }

        public boolean isFull() {
            return rear == (maxSize - 1);
        }

        public boolean isEmpty() {
            return rear == front;
        }

        public void addQueue(int num) {
            if(isFull()) {
                System.out.println("队列已满");
                return;
            }
            rear++;
            array[rear] = num;
        }

        public int popQueue() {
            if(isEmpty()) {
                throw new RuntimeException("队列为空");
            }
            return array[++front];
        }

        public void listQueue() {
            if(isEmpty()) {
                throw new RuntimeException("队列为空");
            }
            for (int i = 0; i < array.length; i++) {
                System.out.printf("数据%d\n", array[i]);
            }
        }

        public int peek() {
            if(isEmpty()) {
                throw new RuntimeException("队列为空");
            }
            return array[front + 1];
        }

    }

}
