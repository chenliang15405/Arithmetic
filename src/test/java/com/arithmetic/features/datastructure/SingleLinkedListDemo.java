package com.arithmetic.features.datastructure;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.Stack;


/**
 * @author alan.chen
 * @date 2020/5/7 10:53 AM
 */
public class SingleLinkedListDemo {


    @Test
    public void revertLinkedListTest() {
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
        revertLinkedList(list.getHead());

        list.list();
    }


    /**
     * 反转链表
     *
     * 使用额外空间
     *
     * @param head
     */
    public void revertLinkedList(HeroNode head) {
        if(head == null || head.next == null ||  head.next.next == null) {
            return;
        }
        // 先获取头节点的下一个节点，即第一个节点
        HeroNode cur = head.next;
        // 创建临时变量，用来保存当前节点的下一个节点
        HeroNode next = null;
        // 创建新的头节点，保存反转链接
        HeroNode revertedHead = new HeroNode(0, "", "");

        while (cur != null) {
            next = cur.next; // 保存下一个节点
            cur.next = revertedHead.next; // 将反转的链表节点链接到当前节点的后面
            revertedHead.next = cur; // 将当前节点设置为头节点的下一个节点
            cur = next;
        }
        // 将反转之后的链表挂载到原头节点
        head.next = revertedHead.next;
    }


    @Test
    public void test() {
        HeroNode head = new HeroNode(1, "1", "1");
        HeroNode node2 = new HeroNode(2, "2", "2");
        HeroNode node3 = new HeroNode(3, "3", "3");
        HeroNode node4 = new HeroNode(4, "4", "4");
        HeroNode node5 = new HeroNode(5, "5", "5");

        head.next = node2;
        head.next.next = node3;
        head.next.next.next = node4;
        head.next.next.next.next = node5;

        System.out.println("反转之前");
        HeroNode cur = head;
        while (cur != null) {
            System.out.print(cur.no + "->");
            cur = cur.next;
        }

        System.out.println("反转之后：");

        HeroNode newHead = reverseLinkedList(head);

        while (newHead != null) {
            System.out.print(newHead.no + "->");
            newHead = newHead.next;
        }
    }

    /**
     * 反转单链表
     *
     * 不使用额外空间
     *
     * @param head
     */
    public HeroNode reverseLinkedList(HeroNode head) {
        HeroNode cur = head;

        HeroNode pre = null;
        HeroNode next = null;
        while (cur != null) {
            // 保存下一个指针
            next = cur.next;
            // 当前指针指向前一个节点
            cur.next = pre;
            // 将当前节点设置为pre
            pre = cur;
            // 当前指针移动到下一个
            cur = next;
        }
        return pre;
    }


    /**
     * 单链表倒数第k个节点
     */
    @Test
    public void test2() {
        HeroNodeLinkedList list = new HeroNodeLinkedList();
        HeroNode node1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode node2 = new HeroNode(2, "小卢", "玉麒麟");
        HeroNode node3 = new HeroNode(3, "林冲", "豹子头");
        list.add(node1);
        list.add(node2);
        list.add(node3);

        System.out.println("单链表遍历");
        list.list();

        HeroNode heroNode = lastIndexNode(list.getHead(), 2);
        System.out.println("倒数第k个节点：" + heroNode);

        HeroNode heroNode2 = lastIndexNode2(list.getHead(), 2);
        System.out.println("倒数第k个节点 第二种方式：" + heroNode2);


        System.out.println("逆序打印单链表");
        reversePrint(list.getHead());
    }

    /**
     * 合并两个有序单链表，合并之后仍然有序
     */
    @Test
    public void test3() {
        HeroNodeLinkedList list = new HeroNodeLinkedList();
        HeroNode node1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode node2 = new HeroNode(8, "小卢", "玉麒麟");
        HeroNode node3 = new HeroNode(10, "林冲", "豹子头");
        list.add(node1);
        list.add(node2);
        list.add(node3);

        HeroNodeLinkedList list2 = new HeroNodeLinkedList();
        HeroNode node5 = new HeroNode(2, "宋江", "及时雨");
        HeroNode node6 = new HeroNode(5, "小卢", "玉麒麟");
        HeroNode node7 = new HeroNode(7, "林冲", "豹子头");
        list2.add(node5);
        list2.add(node6);
        list2.add(node7);


        System.out.println("合并有序链表");
        HeroNode heroNode = mergeSortNode(list.getHead(), list2.getHead());

        HeroNode node = heroNode.next;
        while (node != null) {
            System.out.println(node);
            node = node.next;
        }

    }


    /**
     * 判断单链表是否是环形链表
     */
    @Test
    public void checkCircle() {
        HeroNode node1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode node2 = new HeroNode(8, "小卢", "玉麒麟");
        HeroNode node3 = new HeroNode(10, "林冲", "豹子头");
        HeroNode node4 = new HeroNode(9, "林冲9", "豹子头9");
        HeroNode node5 = new HeroNode(7, "林冲7", "豹子头7");
        HeroNode node6 = new HeroNode(6, "林冲6", "豹子头6");
        node1.next = node2;
        node1.next.next = node3;
        node1.next.next.next = node4;
        node1.next.next.next.next= node5;
        node1.next.next.next.next.next = node6;

        node6.next = node1;

        boolean b = checkCircle(node1);
        System.out.println("单链表是否是环状：" + b);
    }


    /**
     * 删除倒数第k个链表
     */
    @Test
    public void removeKNode() {
        HeroNode node1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode node2 = new HeroNode(8, "小卢", "玉麒麟");
        HeroNode node3 = new HeroNode(10, "林冲", "豹子头");
        HeroNode node4 = new HeroNode(9, "林冲9", "豹子头9");
        HeroNode node5 = new HeroNode(7, "林冲7", "豹子头7");
        HeroNode node6 = new HeroNode(6, "林冲6", "豹子头6");
        node1.next = node2;
        node1.next.next = node3;
        node1.next.next.next = node4;
        node1.next.next.next.next= node5;
        node1.next.next.next.next.next = node6;

        //HeroNode newNode = removeKNode(node1, 3);

        HeroNode newNode = removeKNode2(node1, 3);

        System.out.println("删除之后的链表");
        while (newNode != null) {
            System.out.print(newNode.no + "->");
            newNode = newNode.next;
        }
    }


    /**
     * 删除链表倒数第k个数
     *
     * 使用快指针移动k个数，慢指针和快指针一起移动，快指针到最后，则慢指针到倒数第k个元素
     * 使用pre指针记录数据，删除中间节点
     *
     * @return
     */
    public HeroNode removeKNode2(HeroNode head, int k) {
        // 定义fast指针，将fast移动k个节点，定义slow指针，然后将fast移动到末尾，同步移动slow，则此时slow就指向倒数第k个
        HeroNode fast = head;
        int i = 1; // 从1开始，本身也算一个节点
        // fast指针移动k个节点
        while (fast != null && i < k) {
            fast = fast.next;
            i++;
        }
        // 表示删除的数超过总长度
        if(fast == null) {
            return head;
        }
        // 让slow指针和fast指针同时移动，slow指针会指向倒数第k个节点
        HeroNode slow = head;
        // 定义pre指针指向slow的前一个节点
        HeroNode pre = null;
        while (fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next;
        }
        // 判断pre是否等于null，如果等于null，则表示删除头节点
        if(pre == null) {
            head = head.next;
        } else {
            // 如果不等于null，则表示非头节点
           pre.next = pre.next.next;
        }
        return head;
    }


    /**
     * 删除链表倒数第k个节点，计算总长度，倒数第k则是正数i-k
     *
     * @return
     */
    public HeroNode removeKNode(HeroNode node, int k) {
        if(node == null) {
            return null;
        }
        // 统计长度
        int len = 0;
        HeroNode cur = node;
        while (cur != null) {
            len++;
            cur = cur.next;
        }
        if(k > len) {
            return null;
        }
        // 如果相等，则表示第一个链表就是需要删除的节点，则返回第一个之后的链表
        if(k == len) {
            return node.next;
        }
        // 倒数第k个就是整数len-k
        int i = len - k;

        cur = node;
        HeroNode pre = null;
        int count = 0;
        while (cur != null && count <= i) {
            // 到达第k个，则删除该链表
            if(count == i) {
                pre.next = cur.next;
            }
            pre = cur;
            cur = cur.next;
            count++;
        }
        return node;
    }

    /**
     * 判断单链表是否是环形链表
     *
     * 通过快慢指针
     *
     * @return
     */
    public boolean checkCircle(HeroNode node) {
        if(node == null || node.next == null) {
            return false;
        }
        // 定义快慢指针
        HeroNode fast = node.next; // 快指针在前
        HeroNode slow = node; // 慢指针在后
        // 等待快指针到最后节点，则慢指针在中间，如果是环形链表，则快指针再次走到最后，慢指针也在最后，就会相等
        while (fast != null && fast.next != null) {
            // 如果相等，则环形链表
            if(fast.no == slow.no) {
                return true;
            }
            // 快指针一次2步
            fast = fast.next.next;
            // 慢指针一次1步
            slow = slow.next;
        }
        return false;
    }



    /**
     * 单链表倒数第K个节点  第一种方式
     *
     * 倒数的第k个就是正数的size-k个
     *
     */
    public HeroNode lastIndexNode(HeroNode head, int index) {
        if(head.next == null) {
            return null;
        }
        // 获取该链表的长度
        int nodeLength = getLength(head);
        if(index <= 0 || index > nodeLength) {
            throw new RuntimeException("非法参数位置");
        }
        // 倒数的第k个节点就是正数的size-index个节点
        HeroNode node = head.next;
        for (int i = 0; i < nodeLength - index; i++) {
            node = node.next;
        }

        return node;
    }

    /**
     *
     * 单链表的倒数第k个节点  第二种方式
     *
     * 反转单链表，然后找到第k个
     *
     */
    public HeroNode lastIndexNode2(HeroNode head, int index) {
        if(head.next == null) {
            return null;
        }
        int size = getLength(head);
        if(size <= 0 || index > size) {
            throw new RuntimeException("非法参数位置");
        }
        // 反转单链表
        reverseNode(head);
        // 找到第k个
        int i = 1; // 从1开始计算
        HeroNode node = head.next;
        while (i < index) {
            i++;
            node = node.next;
        }

        return node;
    }




    /**
     * 逆序打印单链表
     *
     * 第一种：使用stack
     * 第二种：先逆序，再打印
     */
    public void reversePrint(HeroNode head) {
        if(head.next == null) {
            return;
        }
        Stack<HeroNode> stack = new Stack<>();

        HeroNode cur = head.next;
        while(cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }
    }



    /**
     * 合并两个有序的单链表，合并之后仍然有序
     *
     * 使用额外的单链表
     */
    public HeroNode mergeSortNode(HeroNode head1, HeroNode head2) {
        if(head1.next == null || head2.next == null) {
            return null;
        }
        HeroNode L = head1.next;
        HeroNode R = head2.next;

        HeroNode heroNode = new HeroNode(1, "", "");
        // 头节点不能改变指向，使用一个新的节点
        HeroNode cur = heroNode;

        while (L != null && R != null) {
            if(L.no < R.no) {
                cur.next = L;
                L = L.next;
            } else {
                cur.next = R;
                R = R.next;
            }
            cur = cur.next;
        }

        if(L != null) {
            cur.next = L;
        }
        if(R != null) {
            cur.next = R;
        }

        return heroNode;
    }



    private void reverseNode(HeroNode head) {
        HeroNode head2 = new HeroNode(1, "", "");

        HeroNode cur = head.next;
        while (cur != null) {
            HeroNode temp = cur.next;

            cur.next = head2.next;
            head2.next = cur;
            cur = temp;
        }

        head.next = head2.next;
    }


    private int getLength(HeroNode head) {
        HeroNode cur = head.next;
        int count = 0;
        while (cur != null) {
            count++;
            cur = cur.next;
        }
        return count;
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
