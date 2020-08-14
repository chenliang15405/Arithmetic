package com.arithmetic.features.leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alan.chen
 * @date 2020/5/16 10:47 AM
 */
public class MaxStringLength {


    /**
     * 找到最长子串
     */
    @Test
    public void maxStringLeng() {
        String str = "abcafcbb";
        StringBuilder subStr = null;
        int maxLenth = 0;

        for (int i = 0; i < str.length(); i++) {
            subStr = new StringBuilder();
            for (int j = i; j < str.length(); j++) {
                if (subStr.indexOf(String.valueOf(str.charAt(j))) == -1) {
                    subStr.append(str.charAt(j));
                } else {
                    break;
                }
            }
            maxLenth = Math.max(maxLenth, subStr.length());
        }

        System.out.println(maxLenth);
    }

    @Test
    public void maxStringLength2() {
        String str = "abcafcbb";

        String subStr = "";
        int max = 0;
        for (int i = 0; i < str.length(); i++) {
            if(subStr.indexOf(str.charAt(i)) != -1) {
                subStr = subStr.substring(subStr.indexOf(str.charAt(i)) + 1);
            }
            subStr += str.charAt(i);
            max = Math.max(subStr.length(), max);
        }
        System.out.println(max);
    }


    @Test
    public void maxStringLength3() {
        String str = "abcafcbb";
        String sub = "";

        int max = 0;
        for (int i = 0; i < str.length(); i++) {
            String s = String.valueOf(str.charAt(i));
            if(sub.contains(s)) {
                sub = sub.substring(sub.indexOf(s) + 1);
            }
            sub += s;
            max = Math.max(max, sub.length());
        }
        System.out.println(max);
    }

    @Test
    public void maxStringLength5() {
        String str = "abcafcbb";

        // key-字符，value-字符的索引
        Map<Character, Integer> map = new HashMap<>();

        int left = 0;
        int max = 0;
        for (int i = 0; i < str.length(); i++) {
            if(map.containsKey(str.charAt(i))) {
                // 重复数的前面有几个数
                left = Math.max(left, map.get(str.charAt(i)) + 1);
            }
            map.put(str.charAt(i), i);
            // 计算最大不重复，当前索引-重复的数的个数 + 1
            max = Math.max(max, i - left + 1);
        }
        System.out.println(max);
    }


    /**
     * 两数之和的数组索引位置，不能自己和自己相加
     *
     */
    @Test
    public void test2() {
        int[] ints = twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(Arrays.toString(ints));
    }

    /**
     * 暴力匹配
     */
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return null;
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }


    /**
     *  整数是否是回文数
     */
    @Test
    public void test3() {
        int num = 1221;

        int reverseNum = 0;
        // 匹配到1半的数字即可判断是否是回文数
        // 所以当原数字小于反转之后的数字即可
        while (num > reverseNum) {
            reverseNum = reverseNum * 10 + num % 10;
            num /= 10;
        }
        // 当原数等于反转的数，或者原数等于反转数/10，因为如果是奇数数字个长度则反转数肯定大于原数，如果是偶数个数字长度
        // 则反转数=原数
        if(num == reverseNum || num == reverseNum/10) {
            System.out.println("是回文数");
            return;
        }
        System.out.println("不是回文数");
    }

    /**
     * 两个数组的中位数
     */
    @Test
    public void twoArrMiddleNum() {
        int[] nums1 = {1, 3};
        int[] nums2 = {2};

        int[] arr = new int[nums1.length + nums2.length];

        for (int i = 0; i < nums1.length; i++) {
            arr[i] = nums1[i];
        }

        int t = nums1.length;
        for (int i = 0; i < nums2.length; i++) {
            arr[t++] = nums2[i];
        }
        Arrays.sort(arr);

        if(arr.length % 2 == 0) {
            int mid = arr.length /2;
            double num = (arr[mid-1] + arr[mid]) * 1.0 /2;
            System.out.println("中位数" + num);
        } else {
            int mid = arr.length /2;
            System.out.println("中位数" + arr[mid]);
        }
    }

    /**
     * 罗马数字转整数
     */
    @Test
    public void romanToInt() {
        String s = "MCMXCIV";

        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            int value = getValue(s.charAt(i));
            int next = 0;
            if(i+1 < s.length()) {
                next = getValue(s.charAt(i + 1));
            }
            if(value < next) {
                sum -= value;
            } else {
                sum += value;
            }
        }
        System.out.println("转换的数字" + sum);

    }

    private int getValue(char str) {
        int value = 0;
        switch (str){
            case 'I':
                value = 1;
                break;
            case 'V':
                value = 5;
                break;
            case 'X':
                value = 10;
                break;
            case 'L':
                value = 50;
                break;
            case 'C':
                value = 100;
                break;
            case 'D':
                value = 500;
                break;
            case 'M':
                value = 1000;
                break;
        }
        return value;
    }


    /**
     * 两数相加
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            throw new IllegalArgumentException("null args");
        }
        ListNode head = new ListNode(0);
        ListNode cur = head;

        int scale = 0;
        while (l1 != null || l2 != null) {
            int x = l1 != null ? l1.val : 0;
            int y = l2 != null ? l2.val : 0;

            int sum = x + y + scale;

            scale = sum / 10;
            sum = sum % 10;

            cur.next = new ListNode(sum);
            if(l1 != null) {
                l1 = l1.next;
            }
            if(l2 != null) {
                l2 = l2.next;
            }
            cur = cur.next;
        }
        if(scale != 0) {
            cur.next = new ListNode(scale);
        }
        return head.next;
    }

    /**
     * 两个链表相加
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            throw new IllegalArgumentException("null args");
        }
        ListNode head = new ListNode(0);
        ListNode cur = head;

        int scale = 0;
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + scale;
            scale = 0;

            if(sum >= 10) {
                sum = sum % 10;
                scale++;
            }
            cur.next = new ListNode(sum);

            l1 = l1.next;
            l2 = l2.next;
            cur = cur.next;
        }

        while (l1 != null) {
            int sum = l1.val + scale;
            scale = 0;
            if(sum >= 10) {
                sum = sum % 10;
                scale++;
            }
            cur.next = new ListNode(sum);

            l1 = l1.next;
            cur = cur.next;
        }
        while (l2 != null) {
            int sum = l2.val + scale;
            scale = 0;
            if(sum >= 10) {
                sum = sum % 10;
                scale++;
            }
            cur.next = new ListNode(sum);

            l2 = l2.next;
            cur = cur.next;
        }
        if(scale != 0) {
            cur.next = new ListNode(scale);
        }

        return head.next;
    }


    /**
     * 合并两个有序链表，并保证合并之后有序
     */
    @Test
    public void linkListMerge() {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(4);
        ListNode l4 = new ListNode(1);
        ListNode l5 = new ListNode(3);
        ListNode l6 = new ListNode(4);

        l1.next = l2;
        l2.next = l3;

        l4.next = l5;
        l5.next = l6;

        ListNode listNode = mergeTwoLists(l1, l4);
        System.out.println(listNode);
    }


    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode p1 = l1;
        ListNode p2 = l2;

        ListNode newNode = new ListNode(0);
        ListNode cur = newNode;

        while (p1 != null && p2 != null) {
            if(p1.val > p2.val) {
                cur.next = new ListNode(p2.val);
                p2 = p2.next;
            } else {
                cur.next = new ListNode(p1.val);
                p1 = p1.next;
            }
            cur = cur.next;
        }

        if(p1 != null) {
            cur.next = p1;
        }
        if(p2 != null) {
            cur.next = p2;
        }
        return newNode.next;
    }


    /**
     * 删除数组重复数据，返回数组长度
     */
    @Test
    public void deleteRepeatItem() {
        int[] num = {0,1,1,1,1,2,2,3,3,4};
        int x = 0;
        for (int i = 1; i < num.length; i++) {
            if(num[i] != num[x]) {
                x++;
                num[x] = num[i];
            }
        }
        System.out.println(Arrays.toString(num));
        System.out.println("不重复的数组长度" + (x + 1));
    }

    /**
     * 删除删除中指定key，并返回数组长度，不使用新数组
     */
    @Test
    public void removeElement() {
        //int[] num = {0,1,2,2,3,0,4,2};
        int[] num = {2,2,2,2};
        //int[] num = {3,2,2,3};
        int val = 2;

        int right = num.length - 1;
        for (int i = 0; i < num.length; i++) {
            while (val == num[i] && right >= i) {
                //swap(num, i, right);
                num[i] = num[right];
                right--;
            }
        }
        System.out.println(Arrays.toString(num));
        System.out.println("不重复的数组长度" + (right + 1));
    }


    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}