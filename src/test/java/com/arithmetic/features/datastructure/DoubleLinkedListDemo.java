package com.arithmetic.features.datastructure;

import org.junit.Test;

/**
 * 双向链表
 *
 * @author alan.chen
 * @date 2020/5/25 11:06 AM
 */
public class DoubleLinkedListDemo {

    @Test
    public void testDoubleLinkedList() {
        HeroNodeLinkedList2 list = new HeroNodeLinkedList2();
        HeroNode2 node1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 node2 = new HeroNode2(2, "小卢", "玉麒麟");
        HeroNode2 node3 = new HeroNode2(3, "林冲", "豹子头");
        list.add(node1);
        list.add(node2);
        list.add(node3);

        list.list();

        System.out.println("头节点" + list.getHead());
        System.out.println("双向链表长度" + list.getLength());

        HeroNode2 node5 = new HeroNode2(2, "像素", "卡丁车");

        list.update(node5);
        System.out.println("修改之后双向链表");
        list.list();

        System.out.println("删除之后的双向链表");
        list.del(node5);
        list.list();

    }

}


class HeroNodeLinkedList2 {
    private HeroNode2 head = new HeroNode2(0, "", "");

    public void add(HeroNode2 node) {
        HeroNode2 temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        // next放到最后一个链表
        temp.next = node;
        // node的pre指向最后一个链表
        node.pre = temp;
    }

    public void del(HeroNode2 node) {
        if(node == null) {
            return;
        }
        boolean flag = false;
        HeroNode2 cur = head.next;
        while(cur != null) {
            if(cur.no == node.no) {
                flag = true;
                // 删除链表
                cur.pre.next = cur.next;
                cur.next.pre = cur.pre;
            }
            cur = cur.next;
        }

        if(!flag) {
            System.out.println("该节点不存在当前双向链表");
        }
    }

    public void update(HeroNode2 node) {
        if(node == null) {
            return;
        }
        boolean flag = false;
        HeroNode2 cur = head.next;

        while (cur != null) {
            if(cur.no == node.no) {
                flag = true;
                if(cur.pre != null) {
                    cur.pre.next = node;
                }
                if(cur.next != null) {
                    cur.next.pre = node;
                }
                node.pre = cur.pre;
                node.next = cur.next;
            }
            cur = cur.next;
        }

        if(!flag) {
            System.out.println("修改成功");
        }
    }

    public void list() {
        HeroNode2 cur = head.next;
        if(cur == null) {
            System.out.println("链表为空");
            return;
        }
        while (cur != null) {
            System.out.println(cur);
            cur = cur.next;
        }
    }

    public HeroNode2 getHead() {
        return head;
    }

    public int getLength() {
        int length = 0;
        HeroNode2 cur = head.next;
        while(cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }

}

class HeroNode2 {

    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next;
    public HeroNode2 pre;

    public HeroNode2(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }

}
