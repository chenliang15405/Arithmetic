package com.arithmetic.features.arithmetic;

import org.junit.Test;

/**
 * @author alan.chen
 * @date 2020/5/16 6:57 PM
 */
public class LinkListReverse {

    /**
     * 反转单链表
     */
    @Test
    public void linkListReverse() {
        HeroNodeLinkedList list = new HeroNodeLinkedList();
        HeroNode node1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode node2 = new HeroNode(2, "小卢", "玉麒麟");
        HeroNode node3 = new HeroNode(3, "林冲", "豹子头");
        list.add(node1);
        list.add(node2);
        list.add(node3);

        System.out.println("没有反转链表之前");
        list.list();

        System.out.println("反转链表之后");

        reverseLinkList(list);

        list.list();

    }


    private void reverseLinkList(HeroNodeLinkedList list) {
        HeroNode head1 = new HeroNode(0, "", "");

        HeroNode head = list.getHead();
        HeroNode cur = head.next;

        while (cur != null) {
            HeroNode next = cur.next;

            cur.next = head1.next;
            head1.next = cur;

            cur = next;
        }

        head.next = head1.next;

    }

}


class HeroNodeLinkedList {
    private HeroNode head = new HeroNode(0, "", "");

    public void add(HeroNode node) {
        HeroNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node;
    }

    public void list() {
        HeroNode cur = head.next;
        if(cur == null) {
            System.out.println("链表为空");
            return;
        }
        while (cur != null) {
            System.out.println(cur);
            cur = cur.next;
        }
    }

    public HeroNode getHead() {
        return head;
    }

    public int getLength() {
        int length = 0;
        HeroNode cur = head.next;
        while(cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }

}

class HeroNode {
    public int no;
    public String name;
    public String nickname;
    public HeroNode next;

    public HeroNode(int no, String name, String nickname) {
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
