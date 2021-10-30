package com.github.bstation.linkedlist;

/**
 * @author tangsong
 * @date 2021/6/3 22:48
 */
public class SmallerEqualsBigger {

    /**
     * 给定一个链表，和一个指定的数字，将单向链表划分为左边小，中间相等，右边大的形式
     *
     *  1. 将链表放入到数组中，在数组上做partition
     *  2. 分成小、中、大三部分，再把各个部分串起来
     *
     *
     */


    /**
     * 使用多个指针，记录每部分的头结点和尾节点，然后将每个部分首尾连接
     *
     */
    public Node way(Node head, int num) {
        if(head == null) {
            return null;
        }
        Node sH = null;
        Node sE = null;
        Node mH = null;
        Node mE = null;
        Node bH = null;
        Node bE = null;

        Node cur = head;
        Node next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = null; // 防止链表连接错误
            if(cur.value > num) {
                if(bH == null) {
                    bH = cur;
                } else {
                    bH.next = cur;
                }
                bE = cur;
            } else if(cur.value < num) {
                if(sH == null) {
                    sH = cur;
                } else {
                    sH.next = cur;
                }
                sE = cur;
            } else {
                if(mH == null) {
                    mH = cur;
                }  else {
                    mH.next = cur;
                }
                mE = cur;
            }
            cur = next;
        }

        // 将每个部分的首尾进行相连
        if(sE != null) {
            sE.next = mH; // 将尾节点连接头结点
            mE = mE != null ? mE : sE; // 哪个部分的尾节点不是空，谁去连大于区域的头结点
        }
        if(mE != null) {
            mE.next = bH;
        }
        // 选择返回哪个部分的头，依次判断是否为空
        return sH != null ? sH : (mH != null ? mH : bH);
    }


}
