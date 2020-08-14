package com.arithmetic.features.datastructure;

import org.junit.Test;

/**
 * 单链表模拟实现栈
 *
 * @author alan.chen
 * @date 2020/5/25 10:52 PM
 */
public class LinkedListStackDemo {

    @Test
    public void test1() {
        LinkedNodeList list = new LinkedNodeList();
        LinkedNode node1 = new LinkedNode(1);
        LinkedNode node2 = new LinkedNode(2);
        LinkedNode node3 = new LinkedNode(3);

        list.add(node1);
        list.add(node2);
        list.add(node3);

        list.show();
    }

    @Test
    public void test2() {
        LinkedNodeList list = new LinkedNodeList();

        LinkedListStack stack = new LinkedListStack(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

        LinkedListStack stack2 = new LinkedListStack(3);
        stack2.push(1);
        stack2.push(2);
        stack2.push(3);
        System.out.println(stack2.pop2());
        System.out.println(stack2.pop2());
        System.out.println(stack2.pop2());
        System.out.println(stack2.pop2());
    }


}

class LinkedListStack {

    private LinkedNodeList linkedList;
    private int size;
    private int index;

    public LinkedListStack(int size) {
        linkedList = new LinkedNodeList();
        this.size = size;
        this.index = 0;
    }

    public void push(int num) {
        if(index == size) {
            throw new RuntimeException("the stack is full!");
        }
        LinkedNode node = new LinkedNode(num);
        linkedList.add(node);
        index++;
    }

    /**
     * 通过减少链表的节点实现弹栈
     * @return
     */
    public Integer pop() {
        if(index == 0) {
            return null;
        }
        index--;
        LinkedNode pop = linkedList.pop();
        return pop.no;
    }

    /**
     * 通过指针获取当前最后一个节点的位置实现弹栈
     * @return
     */
    public Integer pop2() {
        if(index <= 0) {
            return null;
        }
        LinkedNode node = linkedList.pop(index);
        index--;
        return node.no;
    }


}

class LinkedNodeList {
    private LinkedNode first = null;

    public void add(LinkedNode node) {
        LinkedNode cur = first;
        if(first == null) {
            first = node;
        } else {
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = node;
        }
    }

    public void show() {
        LinkedNode cur = first;
        while (cur != null) {
            System.out.println("节点序号：" + cur.no);
            cur = cur.next;
        }
    }

    public LinkedNode pop() {
        LinkedNode cur = first;
        LinkedNode last = first;
        while (cur.next != null) {
            if(cur.next.next == null) {
                last = cur;
            }
            cur = cur.next;
        }
        last.next = null;
        return cur;
    }

    public LinkedNode pop(int index) {
        LinkedNode cur = first;
        for (int i = 0; i < index - 1; i++) {
            cur = cur.next;
        }
        return cur;
    }

}

class LinkedNode {
    public int no;
    public LinkedNode next;

    public LinkedNode(int no) {
        this.no = no;
    }

}
