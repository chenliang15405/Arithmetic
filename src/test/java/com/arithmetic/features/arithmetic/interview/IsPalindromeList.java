package com.arithmetic.features.arithmetic.interview;

import org.junit.Test;

import java.util.Stack;

/**
 * 判断链表是否是回文
 *
 * （需要O(n)个额外空间）
 * 第一种：遍历链表，将所有的数存储到栈中，判断所有的数据是否回文（二分判断或者反向输出等于正向输出）
 *
 * （需要O(n/2)个额外空间）
 * 第二种：使用快慢指针，快指针一次2步，慢指针一次1步，当快指针到最后节点，则慢指针在中间，使用stack保存慢指针后面的
 * 所有数据，然后从头开始遍历指针，比较是否相等。
 *
 * （需要O(1)额外空间）
 * 第三种：使用快慢指针，快指针一次2步，慢指针一次1步，当快指针到最后节点，则慢指针在中间，将慢指针后面的链表反转过来，
 * 然后从最后的节点和开始节点开始向中间移动(任意一个指针为空则结束)，比较是否相等，移动到中间停止，最后将链表后面的再反转回去
 *
 *
 *
 * @author alan.chen
 * @date 2020/6/7 6:49 PM
 */
public class IsPalindromeList {


    @Test
    public void test() {
        Node head = null;
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(3);
        head.next.next.next.next = new Node(2);
        head.next.next.next.next.next = new Node(1);

        boolean palindByStack = isPalindByStack(head);

        System.out.println("是否是回文链表" + palindByStack);

        boolean isPalindByPoint = isPalindByPoint(head);

        System.out.println("是否是回文链表" + isPalindByPoint);
    }

    /**
     * 判断链表是否是回文链表
     * 使用栈
     *
     * 1. 将元素装到栈中，判断所有的数据和再遍历一次链表
     * 2. 将元素装到栈中，二分判断栈中的元素前后是否相等（相对于上面更快一点）
     *
     *
     * @param head
     * @return
     */
    public boolean isPalindByStack(Node head) {
        Stack<Node> stack = new Stack<>();

        Node cur = head;
        // 将节点都保存在栈中
        while (cur != null) {
            stack.add(cur);
            cur = cur.next;
        }
        cur = head;
        // 遍历链表，并判断当前节点和栈中弹出的数据是否一致，栈弹出的是从后向前
        while (cur != null) {
            Node temp = stack.pop();
            if(temp.val != cur.val) {
                return false;
            }
            cur = cur.next;
        }
        return true;
    }


    /**
     * 判断链表是否回文链表
     *
     * 使用N/2额外空间
     *
     * @param head
     * @return
     */
    public boolean isPalindByPoint(Node head) {
        // 因为需要从中间向后的数据都装起来，奇数偶数的节点，需要将慢指针移动后一个
        Node slow = head.next;
        Node fast = head;

        // 当快指针走到最后的时候，慢指针走到中间，如果是偶数，则快指针会走到倒数第2个
        while (fast.next != null && fast.next.next != null)  {
            slow = slow.next;
            fast = fast.next.next;
        }

        Stack<Node> stack = new Stack<>();
        // 将慢指针（中间）后面的数据都用栈装起来
        while (slow !=  null) {
            stack.push(slow);
            slow = slow.next;
        }
        slow = head;
        // 判断栈中的数据是否和链表前面的数据相等
        while (!stack.isEmpty()) {
            Node temp = stack.pop();
            if(temp.val != slow.val) {
                return false;
            }
            slow = slow.next;
        }
        return true;
    }



    public void isPalindByPoint2(Node head) {

    }



    public static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }


}
