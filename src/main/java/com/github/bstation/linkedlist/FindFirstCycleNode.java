package com.github.bstation.linkedlist;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

/**
 * @author tangsong
 * @date 2021/6/5 15:41
 */
public class FindFirstCycleNode {

    /**
     * 给定义两个可能有环也可能无环的单链表，头节点head1和head2.请实现一个函数，如果两个链表相交，请返回相交的第一个节点，
     * 如果不相交，返回Null
     *
     * 要求
     *  如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)
     *
     *
     *  解法：
     *    使用容器的方式，但是额外空间复杂度不满足
     *    使用指针的方式
     */


    /**
     *  使用指针的方式
     *
     *    1. 获取到每个链表的成环的节点，如果没有环则返回null
     *   有以下几种可能：
     *     1> 如果两个链表都没有环，那么这两个链表可能会相交，计算相交的节点，如果相交的条件，那么必定是end节点相等，否则就是两条不相交的链表
     *     2> 如果一个链表有环，一个链表没有环，那么是不可能相交的，因为如果相交，必定会共享一个环
     *     3> 如果两个链表都有环：会有3种情况:
     *        1> 两个链表都有环但是不相交，就直接判断成环的节点是不是同一个节点即可，因为如果成环并且相交，那么必定共享一个环，因为进入到一个环之后就不可能出去了，只有一个next指针
     *        2> 两个链表都有环，并且共享一个环，入环的节点是相同的，那么和上面的第二种可能相同，获取到相交的节点即可
     *        3> 两个链表都有环，并且共享一个环，但是入环的节点是不同的，那么第一个链表入环的节点或者第二个链表入环的节点都是相交的节点
     *
     */
    public Node findNode(Node head1, Node head2) {
        if((head1 == null || head2 == null) || (head1 == null && head2 != null) || (head1 != null && head2 == null)) {
            return null;
        }
        // 获取每个链表的入环节点，如果没有环则返回null
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);

        // 判断两个链表是否都没有环
        if(loop1 == null && loop2 == null) {
            // 如果两个链表都没有环，那么也可能相交
            return noLoop(head1, head2);
        } else if(loop1 != null && loop2 != null) {
            // 如果两个链表都有环，那么也可能相交
            return bothLoop(head1, loop1, head2, loop2);
        }

        // 一个链表有环，一个链表无环这种情况是不可能相交，因为如果相交，那么必定会共享环
        return null;
    }

    /**
     * 计算两个链表都会环的情况的相交的节点
     *
     *  1. 两个链表成环但是不相交
     *  2. 两个链表成环的节点是同一个
     *  3. 两个链表成环的节点不是同一个
     *
     */
    private Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {

        // 如果两个链表的成环的节点是同一个
        if(loop1 == loop2) {
            // 这种情况和两个链表都没有环的情况相同，都是可能会共享一段链表，所以相同的计算方式(唯一的不同是判断长度是计算到成环的节点)
            // 1. 先计算长度
            Node cur1 = head1;
            Node cur2 = head2;
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n > 0) {
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            // 如果两个链表的成环节点不相等，那么可能是链表的不相交，也可能是链表的成环节点不是同一个节点，但是共享一个环

            // 1. 判断两个链表是否共享一个环
            Node temp = loop1.next;
            // 对第一个环进行一次循环，判断其中是否包含节点loop2，如果包含，那么就是共享一个环，肯定相交于loop1或者loop2
            while (temp != loop1) {
                if(temp == loop2) {
                    return loop1;
                }
                temp = temp.next;
            }
            // 如果对第一环进行一次循环，发现其中没有loop2这个节点，那么两个环不是同一个环，肯定不会相交
            return null;
        }

    }

    /**
     * 两个链表都没有环，那么也可能相交，也可能不相交，如果相交，那么这两个链表的最后一个节点肯定是相等的，因为
     * 如果不成环并且相交，那么肯定共享一部分链表，那么结尾的节点肯定是相等的
     *
     * 1. 先计算每个链表的额长度
     * 2. 让长度较长的链表先走到 长度差的步数
     * 3. 然后两个开始同时走，每次走一步，如果相遇，那么就是橡相交的节点（因为如果相交，那么必定在后半段是相交的）
     *
     */
    private Node noLoop(Node head1, Node head2) {
        // 计算两个链表的长度
        Node cur1 = head1;
        Node cur2 = head2;
        // 代表两个链表的高度差
        int n = 0;
        while (cur1 != null) {
            n++;
            cur1 = cur1.next;
        }
        // 对n--，
        while (cur2 != null) {
            n--;
            cur2 = cur2.next;
        }
        // 判断两个链表的最后一个节点是否相等
        if(cur1 != cur2) {
            // 如果两个链表的末尾节点不相等，那么必定不相交
            return null;
        }
        // 如果两个链表的末尾节点相等，那么代表相交了，计算相交的节点
        cur1 = n > 0 ? head1 : head2; // 找到较长的链表的头节点
        cur2 = cur1 == head1 ? head2 : head1; // 找到较短的链表的头节点
        n = Math.abs(n); // 计算绝对值，因为n代表的是高度差

        while (n > 0) {
            n--;
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        // 返回相交的节点
        return cur1;
    }

    /**
     * 获取当前链表的成环节点，如果不成环则返回Null
     */
    private Node getLoopNode(Node head) {
        // 定义快指针，慢指针
        Node slow = head;
        Node fast = head.next;
        while (slow != fast) {
            // 如果fast指针走到了最后一个节点，或者即将走到最后一个节点，因为最后一个节点是null，那么肯定是没有环的
            if(fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        // 如果出了循环，那么肯定是有环的，并且当前两个指针相遇
        // 让快指针从head重新开始每次走一步，慢指针同步走，那么下次相遇的节点一定是成环的节点
        fast = head;
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        // 重新开始走，那么相遇的节点一定是成环的节点
        return slow;
    }


}
