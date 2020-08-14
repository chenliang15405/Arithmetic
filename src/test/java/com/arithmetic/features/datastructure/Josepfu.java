package com.arithmetic.features.datastructure;

import org.junit.Test;


/**
 * 约瑟夫（约瑟夫环）问题
 *
 * 在圆环中，通过指定m个数开始计数，第m个出圈，最后圈中只有一个数
 *
 * 使用单链表构建圆环并实现
 *
 * @author alan.chen
 * @date 2020/5/25 10:38 PM
 */
public class Josepfu {

    @Test
    public void test() {
        NodeList list = new NodeList();
        list.add(10);
        list.list();

        // 1 2 3 4 5 6 7 8 9 10
        // 指定m个数进行出列
        int offset = 12;
        int startNo = 1;
        int size = 10;
        list.countNodeByOffset(startNo, offset, size);
    }

    @Test
    public void test2() {
        Josepfu test = new Josepfu();
        synchronized (test) {
            try {
                test.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class NodeList {
    private Node first = null;


    public void add(int nums) {
        if(nums < 0) {
            throw new RuntimeException("the args is not available");
        }
        Node cur = null;
        for (int i = 1; i <= nums; i++) {
            Node temp = new Node(i);
            if(i == 1) {
                first = temp;
                first.next = temp;
                cur = first;
            } else {
                cur.next = temp;
                temp.next = first;
                cur = temp;
            }
        }
    }

    public void list() {
        if(first == null) {
            return;
        }
        Node cur = first;
        while (true) {
            System.out.println("当前节点编号：" + cur.no);
            if(cur.next == first) {
                break;
            }
            cur = cur.next;
        }
    }

    /**
     * 每offset个数就会出链表，最后剩下一个数
     * @param startNo 开始的序号
     * @param offset 数多少个数开始出链表
     * @param size 一共多少个节点
     */
    public void countNodeByOffset(int startNo, int offset, int size) {
        if(offset < 0) {
            throw new RuntimeException("the args is not available");
        }
        // 取余，防止溢出
        offset = offset % size;
        // 辅助指针，指向first的前一个节点
        Node helper = first;
        //1. helper节点指向fist节点的前一个节点
        while (helper.next != first) {
            helper = helper.next;
        }
        // 2. 移动节点到指定的开始节点
        for (int i = 0; i < startNo - 1; i++) {
            helper = helper.next;
            first = first.next;
        }
        // 3. 开始计数出圈
        while (true) {
            if(helper == first) {
                break;
            }
            for (int i = 0; i < offset - 1; i++) {
                helper = helper.next;
                first = first.next;
            }
            System.out.printf("节点%d出圈\n", first.no);
            // 将first指向的节点出圈,并将first指向后一位
            first = first.next;
            helper.next = first;
        }
        System.out.println("最后在圈中的节点编码：" + helper.no);
    }

}


class Node {
    public int no;
    public Node next;

    public Node(int no) {
        this.no = no;
    }
}
