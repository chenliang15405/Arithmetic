package com.arithmetic.features.datastructure.hashtable;

import org.junit.Test;


/**
 * @author alan.chen
 * @date 2020/5/30 4:54 PM
 */
public class HashTableDemo {

    @Test
    public void test() {
        EmpNode node1 = new EmpNode(1, "卡莎");
        EmpNode node2 = new EmpNode(2, "诸葛");
        EmpNode node3 = new EmpNode(3, "唐三");
        EmpNode node4 = new EmpNode(5, "唐宋");
        EmpNode node5 = new EmpNode(6, "demo");
        EmpNode node6 = new EmpNode(9, "demo2");

        HashTable table = new HashTable(8);

        table.add(node1);
        table.add(node2);
        table.add(node3);
        table.add(node4);
        table.add(node5);
        table.add(node6);

        table.list();

        EmpNode empNode = table.get(3);
        System.out.println("根据id找到的节点" + empNode);
    }

}

// HashTable类
class HashTable {

    // 维护了数组，数组中保存的是链表
    private EmpLinkedList[] list;

    private int maxSize;

    public HashTable(int maxSize) {
        this.maxSize = maxSize;
        list = new EmpLinkedList[maxSize];

        // 初始化数组对象，还需要将数组中的所有链表初始化
        for (int i = 0; i < maxSize; i++) {
            list[i] = new EmpLinkedList();
        }
    }

    public void add(EmpNode node) {
        if(node == null) {
            throw new IllegalArgumentException("illegal args");
        }
        int hash = hash(node.id);
        list[hash].add(node);
    }


    public void list() {
        for (int i = 0; i < maxSize; i++) {
            list[i].list(i);
        }
    }

    public EmpNode get(int id) {
        int hash = hash(id);
        if(hash < 0 || hash > maxSize) {
            throw new IllegalArgumentException("pls correct id");
        }
        EmpLinkedList empLinkedList = list[hash];
        return empLinkedList.get(id);
    }

    private int hash(int id) {
        return id % maxSize;
    }

}

// 单链表
class EmpLinkedList {

    private EmpNode head;


    // 增加
    public void add(EmpNode node) {
        if(head == null) {
            head = node;
            return;
        }
        // 如果不等于null，则找到最后一个节点，并添加到该节点的next
        EmpNode cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = node;
    }

    // 遍历
    public void list(int no) {
        // 遍历链表
        if(head == null) {
            System.out.println("第" + no + "条链表为空");
            return;
        }
        EmpNode temp = head;
        while (temp != null) {
            System.out.print("第" + no + "条链表信息：" + temp);
            temp = temp.next;
        }
        System.out.println();
    }

    // 根据id查询节点信息
    public EmpNode get(int id) {
        EmpNode cur = head;
        return digui(cur, id);
    }

    private EmpNode digui(EmpNode cur, int id) {
        if(cur == null) {
            return null;
        }
        if(cur.id == id) {
            return cur;
        }
        return digui(cur.next, id);
    }

}

// 链表节点
class EmpNode {
    public int id;
    public String name;
    public EmpNode next;


    public EmpNode(int no, String name) {
        this.id = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "EmpNode{" +
                "no=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}