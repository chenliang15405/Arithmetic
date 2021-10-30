package com.arithmetic.features.issue;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.security.Key;
import java.util.*;


/**
 * @author alan.chen
 * @date 2020/5/21 11:35 AM
 */
public class JueJinIssue {


    /**
     * 将字符串中的空格替换为%20
     */
    @Test
    public void test1() {
        String str = "We Are People";

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            String item = str.charAt(i) == ' ' ? "%20" : String.valueOf(str.charAt(i));
            builder.append(item);
        }
        System.out.println(builder.toString());
    }


    /**
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     *
     * 第一种方法：使用集合 在遍历数组时加入元素到集合，每次重复则删除，最后集合中剩下的就是唯一的树
     * 第二种：使用 ^ 位运算，n^n=0，n^0=n
     *
     */
    @Test
    public void singleNumber() {
        int[] nums = new int[]{3,1,2,1,2};

        List<Integer> list = new ArrayList<>();

        // 使用集合存储每个元素，如果有该元素，则直接删除掉该元素，遍历到最后肯定剩下一个元素
        for(int i = 0; i < nums.length; i++) {
            if(list.contains(nums[i])) {
                // int类型不能直接删除，否则认为调用的是remove(int i)方法，而不是调用remove(Object o)
                list.remove((Integer)nums[i]);
            } else {
                list.add(nums[i]);
            }
        }
        System.out.println("只出现一次的数字：" + list.get(0));

        Thread t = new Thread();
        t.start();

    }


    /**
     * 第一次出现重复次数最多的字符
     */
    @Test
    public void test20() {
        String a = "eaebaaccdopcac";

        HashMap<Character, Integer> map = new HashMap<>();

        int max = 0;
        char x = a.charAt(0);

        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            if(map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
            if(map.get(c) > max) {
                max = map.get(c);
                x = c;
            }

        }

        System.out.println("最多char：" + x);
        System.out.println("最多：" + max);
    }

    /**
     * 最长公共前缀
     */
    @Test
    public void test2() {
        String[] arr = {"flozaaaw", "flower", "fly"};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));


        StringBuilder builder = new StringBuilder();
        char[] first = arr[0].toCharArray();
        char[] last = arr[arr.length - 1].toCharArray();

        int length = first.length > last.length ? last.length : first.length;

        for (int i = 0; i < length; i++) {
            if (first[i] != last[i]) {
                break;
            }
            builder.append(first[i]);
        }

        System.out.println("最长公共前缀" + builder.toString());
    }

    /**
     * 字符串排序是通过比较字符串的开头的字母的大小来进行比较的，如果相等，则后一位比较，一旦不相等，
     * 则可以判断字符串的大小了
     * <p>
     * 字符串的快速排序
     */
    @Test
    public void test3() {
        String[] input = new String[]{"tmc", "apad", "apache", "nihao", "nihaoa"};
        quickSort(input, 0, input.length - 1);

        System.out.println(Arrays.toString(input));
    }


    /**
     * 验证是否回文串
     */
    @Test
    public void test6() {
        String str = "A man, a plan, a canal:Panama";
        //String str = "A man,nana";
        str = str.toLowerCase();
        int left = 0;
        int right = str.length() - 1;
        boolean flag = false;
        while(left < right) {
            // 如果都是字母，则判断是否相等,如果不相等，直接终止，非回文串
            if(isDigest(str.charAt(left)) && isDigest(str.charAt(right))) {
                flag = str.charAt(left) == str.charAt(right);
                if(!flag) {
                    break;
                }
                left++;
                right--;
            }
            // 如果左边不是字符，则向右移动
            if(!isDigest(str.charAt(left))) {
                left++;
            }
            // 如果右边不是回文串，则向左移动
            if(!isDigest(str.charAt(right))) {
                right--;
            }
        }

        System.out.println(flag);
    }

    private boolean isDigest(char c) {
        if(97 <= c && c <= 122) {
            return true;
        }
        return false;
    }


    /**
     * 最长回文串(可以构造的最长个回文串)
     *
     * 字符串：abcdbcdd
     * 构造的回文串：bcdadcb  长度为7
     */
    @Test
    public void test4() {
        String str = "abcdbcdd";
        HashSet<Character> set = new HashSet<>();
        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            if(set.contains(str.charAt(i))) {
                set.remove(str.charAt(i));
                count++;
            } else {
                set.add(str.charAt(i));
            }
        }

        if(set.isEmpty()) {
            count = count * 2;
        } else {
            count = count * 2 + 1;
        }
        System.out.println(count);
    }

    /**
     * 最长回文字串
     *
     * 字符串：baba
     *
     * 回文字串：aba
     */
    @Test
    public void test5() {
        //String str = "aba";
        String str = "bb";

        int max = 0;
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j <= str.length(); j++) {
                String text = str.substring(i, j);
                if(isPalidStr(text) && text.length() > max) {
                    result = text;
                    max = text.length();
                }
            }
        }

        System.out.println("最长回文子串：" + result);
    }

    /**
     * 单词字符串反转
     */
    @Test
    public void test7() {
        String str = "   the sky is blue";
        String[] words = str.trim().split(" +");

        StringBuilder builder = new StringBuilder();

        for (int i = words.length - 1; i >= 0; i--) {
            builder.append(words[i]).append(" ");
        }
        String reverseStr = builder.substring(0, builder.length() - 1);

        System.out.println(reverseStr);
    }

    /**
     * 有序数组查询指定值
     */
    @Test
    public void test8() {
        int [] arr = {8, 10, 17, 20, 55};
        int key = 10;
        // 递归二分查询
        Integer integer = binarySearch(arr, key, 0, arr.length - 1);
        System.out.println(integer);

        System.out.println("非递归方式查找");
        // 非递归方式查询
        Integer search = search(arr, key);
        System.out.println(search);
    }

    /**
     * 单词反转，时间复杂度 N
     *
     * 输入：hello
     * 输出：olleh
     */
    @Test
    public void test9() {
        String str = "A man, a plan, a canal: Panama";

        StringBuilder builder = new StringBuilder();
        for (int i = str.length() - 1; i >=  0; i--) {
            builder.append(str.charAt(i));
        }
        System.out.println(builder.toString());
    }

    /**
     * 单词反转，时间复杂度 N/2
     *
     * 输入：hello
     * 输出：olleh
     */
    @Test
    public void test10() {
        String str = "A man, a plan, a canal: Panamac";

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length / 2; i++) {
            swap(chars, i, chars.length - 1 - i);
        }
        String s = new String(chars);
        System.out.println(s);
    }

    /**
     * 字符串转换为正数，如果数值为0或者不符合数字时返回0
     * 使用额外集合map
     *
     * 输入：+217338930    1a989
     * 输出：217338930     0
     */
    @Test
    public void test11() {
        String str = "+23910526";
        int start = str.charAt(0) == '+' ? 1 : 0;

        Map<Character, Integer> map = new HashMap<>();
        map.put('0', 0);
        map.put('1', 1);
        map.put('2', 2);
        map.put('3', 3);
        map.put('4', 4);
        map.put('5', 5);
        map.put('6', 6);
        map.put('7', 7);
        map.put('8', 8);
        map.put('9', 9);

        long num = 0;
        int seating = 1;
        for (int i = str.length() - 1; i >= start; i--) {
            if(str.charAt(i) > '9' || str.charAt(i) < '0') {
               num = 0;
               break;
            }
            num += map.get(str.charAt(i)) * seating;
            seating = seating * 10;
        }

        System.out.println(num);
    }

    /**
     * 字符串转换为正数，如果数值为0或者不符合数字时返回0
     * 不使用map的方法，字符数字对应的ASCII表中的值-'0'字符对应的ASCII值就是真实的对应的数字  '0'~'9' 对应 48~57
     *
     * 输入：+217338930    1a989
     * 输出：217338930     0
     */
    @Test
    public void test12() {
        String str = "+23910526";
        int start = str.charAt(0) == '+' ? 1 : 0;

        long num = 0;
        for (int i = start; i < str.length(); i++) {
            if(str.charAt(i) > '9' || str.charAt(i) < '0') {
                num = 0;
                break;
            }
            num = num * 10 + (str.charAt(i) - '0');
        }

        System.out.println(num);
    }


    /**
     * 字符串的加减乘除
     */
    @Test
    public void test13() {
        Map<String, Integer> map = new HashMap<>();
        map.put("零", 0);
        map.put("一", 1);
        map.put("二", 2);
        map.put("三", 3);
        map.put("四", 4);
        map.put("五", 5);
        map.put("六", 6);
        map.put("七", 7);
        map.put("八", 8);
        map.put("九", 9);

        String str = "一+五";
        // TODO 先转换为数字，然后进行计算

        String[] strNums = str.split("[+|-|*|//]");
        int num = 0;
        if(str.contains("+")) {
            num = map.get(strNums[0]) + map.get(strNums[1]);
        } else if(str.contains("-")) {
            num = map.get(strNums[0]) - map.get(strNums[1]);
        } else if(str.contains("*")) {
            num = map.get(strNums[0]) * map.get(strNums[1]);
        } else if(str.contains("/")) {
            num = map.get(strNums[0]) / map.get(strNums[1]);
        }
        System.out.println(num);
    }


    /**
     * 中文字符转换为阿拉伯数字
     *
     * 十万九千零六十  --> 109060
     */
    @Test
    public void test14() {
        String str = "十万九千零六十一";

        char[] cnArr = {'一', '二', '三', '四', '五', '六', '七', '八', '九'};
        char[] symbolArr = {'十', '百', '千', '万', '亿'};

        int temp = 1; // 每个数字的实际值
        int result = 0;
        int count = 0; // 是否有单位

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {
                if(c == cnArr[j]) {
                    // 如果匹配到数字
                    if(count != 0) {
                        // 将前一位已经匹配到单位的数加到结果集
                        result += temp;
                        // 置为初始置
                        temp = 1;
                        count = 0;
                    }
                    temp = j + 1; // 下标值加1就是对应的值
                }
            }
            // 可以在这里增加判断，如果当前轮匹配到了数字，则不进行符号的匹配
            for (int j = 0; j < symbolArr.length; j++) {
                if(c == symbolArr[j]) {
                    switch (j) {
                        case 0:
                            temp *= 10;
                            break;
                        case 1:
                            temp *= 100;
                            break;
                        case 2:
                            temp *= 1000;
                            break;
                        case 3:
                            temp *= 10000;
                            break;
                        case 4:
                            temp *= 100000000;
                            break;
                        default:
                            break;
                    }
                    count++;
                }
            }
            // 如果匹配到最后一位数，则将该数加到reuslt
            if(i == str.length() - 1) {
                result += temp;
            }
        }

        System.out.println("阿拉伯数字：" + result);
    }




    /**
     * 左旋字符串
     *
     * abcXYZdef 左旋3位 -> XYZdefabc
     */
    @Test
    public void test15() {
        String str = "abcXYZdef";
        int offset = 3;

        offset  = offset % str.length();  // 防止超出长度，还是循环移动
        String ss = str.substring(0, offset);
        String newStr = str.substring(offset);
        newStr += ss;

        System.out.println(newStr);
    }

    /**
     * 旋转字符串
     *
     * 将字符转的最左边的字符移动到字符串最右边
     *
     * abcde 经过若干次旋转之后是否包含 cdeab
     *
     */
    @Test
    public void test16() {
        String str = "abcde";
        String key = "cdeab";

        boolean flag = false;
        if(str.length() == key.length()) {
            flag = (str + str).contains(key);
        }
        System.out.println("是否包含key：" + flag);
    }

    /**
     * 字符串b是否包含字符串a，如果包含则返回下标
     *
     * TODO KMP算法
     *
     */
    @Test
    public void test17() {
        String a = "BBC ABCDAB ABCDABCDABDE";
        String b = "ABCDABD";

        int aLength = a.length();
        int bLength = b.length();

        int lefta = 0;
        int leftb = 0;

        while (lefta < aLength && leftb < bLength) {
            if(a.charAt(lefta) == b.charAt(leftb)) {
                lefta++;
                leftb++;
            } else {
                // 让left回溯到之前的位置，并移动一位
                lefta = lefta - leftb + 1;
                leftb = 0;
            }
        }
        if(leftb == bLength) {
            System.out.println("匹配到的下标：" + (lefta - leftb));
        } else {
            System.out.println("没有匹配到");
        }

    }


    /**
     * 字符串计算： "1+2*30-8+10/2"
     */
    @Test
    public void test18() {

    }


    /**
     * 爬楼梯
     */
    @Test
    public void palouti() {
        int n = 5;
        int way = getWay(n);
        System.out.println("总共的方式有" + way);
    }

    /**
     * 将整数倒序
     */
    @Test
    public void reverseInt() {
        int num = -123;

        StringBuilder result = new StringBuilder();
        String s = String.valueOf(num);
        if(s.charAt(0) == '-') {
            s = s.substring(1);
            result.append("-");
        }
        for (int i = s.length() - 1; i >= 0; i--) {
            result.append(s.charAt(i));
        }
        long val = Long.valueOf(result.toString());
        if(val > Integer.MAX_VALUE || val < Integer.MIN_VALUE) {
            System.out.println("0");
        } else {
            System.out.println((int)val);
        }

    }


    @Test
    public void reverseInt2() {
        int num = 1239999399;

        long result = 0;
        while (num != 0) {
            int x = num % 10;
            num /= 10;
            result = result * 10 + x;
        }
        if(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            System.out.println("0");
            return;
        }

        System.out.println(result);
    }

    @Test
    public void reverseInt3() {
        int num = 12399993;

        int result = 0;

        while(num != 0) {
            int n = num % 10;
            num /= 10;

            result = result * 10 + n;

            if(result > Integer.MAX_VALUE / 10 || result < Integer.MIN_VALUE / 10) {
                System.out.println("0");
                return;
            }
        }
        System.out.println(result);
    }

    @Test
    public void reverseInt5() {
        int num = 12399993;

        int result = 0;

        while(num > 0) {
            int x = num % 10;
            num /= 10;
            result = result * 10 + x;
            if(result > Integer.MAX_VALUE / 10 || result < Integer.MIN_VALUE / 10) {
                return;
            }
        }

        System.out.println(result);
    }

    /**
     * 字符串数据转换为整数
     */
    @Test
    public void strConvertInt() {
        String str = "-6239999399";
        //String str = "words and 987";
        //String str = "3.14159";
        int num = 0;
        int x = 0;

        str = str.trim();
        if("".equals(str)) {
            System.out.println("0");
            return;
        }
        if(!Character.isDigit(str.charAt(0)) && str.charAt(0) != '-' && str.charAt(0) != '+') {
            System.out.println("0");
            return;
        }
        if(str.charAt(0) == '-') {
            x = 1;
        }
        String[] split = str.split("\\.");
        str = split[0];
        for (int i = x; i < str.length(); i++) {
            if(isNum(str.charAt(i))) {
                if(num > Integer.MAX_VALUE / 10) {
                    System.out.println(Integer.MAX_VALUE);
                    return;
                }
                if(num < Integer.MIN_VALUE / 10) {
                    System.out.println(Integer.MIN_VALUE );
                    return;
                }
                num = x == 1 ? num * 10 + (str.charAt(i) - '0') * -1 : num * 10 + (str.charAt(i) - '0');
            }
        }
        System.out.println(num);
    }

    public boolean isNum(char c) {
        if(c - '0' >= 0 && c - '0' <= 9) {
            return true;
        }
        return false;
    }

    /**
     * 统计最长公共前缀
     */
    @Test
    public void test() {
        String[] strs = {"flower","flow","flight"};
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++)
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()){
                    System.out.println("");
                }
            }
    }


    /**
     * 统计 b字符串在a字符串中出现的次数
     */
    @Test
    public void strTimes() {
        String a = "abc cddabcwwerfabckkbccba";
        String b = "abc";

        int count = 0;
        while(a.indexOf(b) > -1) {
            count++;
            a = a.replaceFirst(b, "");
        }

        System.out.println("统计的次数：" + count);
    }


    /**
     * 循环打印二维数组(待完善)
     */
    @Test
    public void printDoubleArr() {
        //int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        //int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        int row = matrix.length;
        int col = matrix[0].length;
        boolean[][] visited = new boolean[row][col];
        int[] arr = new int[row*col];
        setWay(matrix, visited, arr, 0, 0, 0);

        System.out.println(Arrays.toString(arr));
    }

    /**
     * 转圈打印矩阵
     */
    @Test
    public void printMatirxArr() {
        //int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        //int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        int row = matrix.length - 1;
        int col = matrix[0].length -1 ;
        int tR = 0;
        int tC = 0;
        List<Integer> list = new ArrayList<>();
        while (tR <= row && tC <= col) {
            printMatirx(matrix, tR++, tC++, row--, col--, list);
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
    }

    /**
     * 矩阵行转列
     */
    @Test
    public void martixZhuanzhi() {
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};

        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                newMatrix[i][j] = matrix[j][i];
            }
        }

        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[i].length; j++) {
                System.out.print(newMatrix[i][j]);
            }
            System.out.println();
        }
    }

    @Test
    public void generateMatrix() {
        int n = 3;
        int[][] arr = new int[3][3];
        int num = 1;
        int l = 0;
        int r = n - 1;
        int t = 0;
        int b = n - 1;
        while(num <= n*n) {
            for (int i = l; i <= r; i++) {
                arr[t][i] = num++;
            }
            t++;
            for (int i = t; i <= b; i++) {
                arr[i][r] = num++;
            }
            r--;
            for (int i = r; i >= l; i--) {
                arr[b][i] = num++;
            }
            b--;
            for (int i = b; i >= t; i--) {
                arr[i][l] = num++;
            }
            l++;
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }



    /**
     * 整数反转
     */
    @Test
    public void intReverse() {
        int num = -123;
        int sum = 0;
        while (num != 0) {
            int x = num % 10; // 负数对10取余得到的余数都是负数
            num /= 10;

            sum = sum * 10 + x;
        }
        Assert.assertEquals(-321, sum);
    }

    /**
     * 三数之和
     */
    @Test
    public void threeSum() {
        // 输出整数数组
        int[] nums = {-1, 0, 1, 2, -1, -4};

        List<int[]> list = threeSum(nums);

        list.stream().forEach(item -> System.out.println(Arrays.toString(item)));
    }

    private List<int[]> threeSum(int[] nums) {
        if(nums == null || nums.length < 1) {
            return new ArrayList<>();
        }
        List<int[]> list = new ArrayList<>();
        // 对数组排序
        Arrays.sort(nums);
        // 循环数组
        for (int i = 0; i < nums.length; i++) {
            // 因为是排序数组，所以当前值大于0则后面的所有值不会等于0
            if(nums[i] > 0) {
                break;
            }
            // 已经是排序数组，所以去重
            if(i > 0 && nums[i] == nums[i-1]) {
                continue;
            }

            int L = i + 1;
            int R = nums.length - 1;

            // 每一轮确定一个元素为基础开始相加
            while (L < R) {
                if(nums[i] + nums[L] + nums[R] == 0) {
                    // 将该元素添加到集合中
                    list.add(new int[]{nums[i], nums[L], nums[R]});
                    // 指针改变
                    L++;
                    R--;
                    // 已经有符合的数据，则去重
                    while (L < R && nums[L] == nums[L - 1]) {
                        L++;
                    }
                    //去重
                    while(L < R && nums[R] == nums[R + 1]) {
                        R--;
                    }
                } else if(nums[i] + nums[L] + nums[R] > 0) {
                    R--;
                } else if(nums[i] + nums[L] + nums[R] < 0) {
                    L++;
                }
            }
        }
        return list;
    }


    /**
     * 旋转数组
     */
    @Test
    public void rotate() {
        int[] nums = new int[]{1,2,3,4,5,6,7};
        int k = 3;

        for(int i = 0; i < k; i++) {
            int index = nums.length - k + i;

            while(index != i) {
                swap(nums, index - 1, index--);
            }
        }
    }
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    @Test
    public void fibonaci() {
        int n = 5;
        for (int i = 0; i < n; i++) {
            System.out.println(fib(i));
        }
        System.out.println("==============");
        for (int i = 1; i < n; i++) {
            System.out.println(fibNo(i));
        }
    }

    /**
     * 非递归斐波那契数列
     */
    private int fibNo(int n) {
        int[] arr = new int[n + 1];
        arr[0] = 0;
        arr[1] = 1;

        for (int i = 2; i <= n; i++) {
            arr[i] = arr[i-1] + arr[i-2];
        }
        return arr[n];
    }

    private int fib(int n) {
        if(n == 0 ) {
            return 1;
        }
        if(n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    /**
     * 矩阵置0
     *
     *  其中有点问题，如果矩阵中有Integer.MAX_VALUE，那么就无法通过case
     */
    @Test
    public void setZeros() {
        int[][] matrix = {{1,2,3},{4,0,6},{7,8,9}};

        setZeroes(matrix);

        for (int[] ints : matrix) {
            System.out.println(Arrays.toString(ints));
        }
    }

    /**
     * 使用栈对另外一个栈中数据排序
     */
    @Test
    public void stackSort() {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(3);
        stack.push(5);
        stack.push(2);
        stack.push(7);
        stack.push(6);

        Stack<Integer> help = new Stack<>();

        while (!stack.isEmpty()) {
            Integer cur = stack.pop();
            // help中保存的总是目前的最大值
            if(!help.isEmpty() && help.peek() < cur) {
                stack.push(help.pop());
            }
            help.push(cur);
        }

        while (!help.isEmpty()) {
            stack.push(help.pop());
        }
        System.out.println(stack);
    }

    /**
     * 最小公倍数：两个数的乘积=2个数的最大公约数*最小公倍数
     */
    @Test
    public void te1() {
        int m = 502;
        int n = 94278;
        int a = (m*n)/gcd(m,n);
        System.out.println(a);
    }

    public int gcd(int p, int q){
        int max = Math.max(p, q);
        while((max % p != 0) || (max % q) != 0) {
            max--;
        }
        return max == 0 ? 1 : max;
    }

    public void setZeroes(int[][] matrix) {
        int endR = matrix.length;
        int endC = matrix[0].length;

        int INF = Integer.MAX_VALUE;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < endC; j++) {
                if(matrix[i][j] == 0) {
                    infect(matrix, i, j, endR, endC);
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < endC; j++) {
                if(matrix[i][j] == INF) {
                    matrix[i][j] = 0;
                }
            }
        }
    }



    private void infect(int[][] matrix, int i, int j, int R, int C) {
        int col = 0;
        while (col < C) {
            matrix[i][col++] = Integer.MAX_VALUE;
        }
        int row = 0;
        while (row < R) {
            matrix[row++][j] = Integer.MAX_VALUE;
        }
    }


    /**
     * 转圈打印矩阵
     * @param matrix 矩阵
     * @param tR 左上角行
     * @param tC 左上角列
     * @param dR 右下角行
     * @param dC 右下角列
     * @param list 保存的打印顺序
     */
    public void printMatirx(int[][] matrix, int tR, int tC, int dR, int dC, List<Integer> list) {
        int a = tR;
        int b = tC;
        int c = dR;
        int d = dC;
        if(a == c) {
            // 只有一行的情况
            while (b <= d) {
                //System.out.println(matrix[a][b++] + " ");
                list.add(matrix[a][b++]);
            }
        }
        if(b == d) {
            // 如果只有一列的情况
            while (a <= c) {
                //System.out.println(matrix[a++][b] + " ");
                list.add(matrix[a++][b]);
            }
        }
        while (b < dC) {
            //System.out.println(matrix[tR][b++] + "  ");
            list.add(matrix[tR][b++]);
        }
        while (a < dR) {
            //System.out.println(matrix[a++][dC] + "  ");
            list.add(matrix[a++][dC]);
        }
        while (d > tC) {
            //System.out.println(matrix[dR][d--] + "  ");
            list.add(matrix[dR][d--]);
        }
        while (c > tR) {
            //System.out.println(matrix[c--][tC] + "  ");
            list.add(matrix[c--][tC]);
        }
    }

    /**
     * 递归打印矩阵，TODO 多层级无法打印
     *
     * @return
     */
    private boolean setWay(int[][] matrix, boolean[][] visited, int[] arr, int x, int y, int t) {
        if(x >= matrix.length || x < 0) {
            return false;
        }
        if(y >= matrix[0].length || y < 0) {
            return false;
        }

        try {
            if(!visited[x][y]) {
                visited[x][y] = true;
                arr[t++] = matrix[x][y];
                // 向右
                if(setWay(matrix, visited, arr, x, ++y, t)) {
                    return true;
                } else if(setWay(matrix, visited, arr, ++x, --y, t)) { // 向下
                    return true;
                } else if(setWay(matrix, visited, arr, --x, --y, t)) { // 向左
                    return true;
                } else if(setWay(matrix, visited, arr, --x, ++y, t)) { // 向上
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    private int getWay(int n) {
        if(n == 1) {
            return 1;
        }
        if(n == 2) {
            return 2;
        }
        return getWay(n - 1) + getWay(n - 2);
    }


    /**
     * 二分查找，查找有序数组的指定某个值
     *
     * 先让数组有序，然后查找key
     *
     */
    private int binarySearch(int[] arr, int key, int left, int right) {
        if(left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        if(arr[mid] > key) {
            mid = binarySearch(arr, key, left, mid - 1);
        } else if(arr[mid] < key) {
            mid = binarySearch(arr, key, mid + 1, right);
        }
        return mid;
    }

    /**
     * 二分查找的非递归方式
     *
     */
    private Integer search(int[] arr, int key) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if(arr[mid] > key) {
                right = mid - 1;
            } else if(arr[mid] < key) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }


    private boolean isPalidStr(String str) {
        boolean flag = false;
        for (int i = 0; i < str.length() / 2; i++) {
            if(str.charAt(i) == str.charAt(str.length() - i - 1)) {
                flag = true;
            }
        }
        return flag;
    }


    private void quickSort(String[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int[] p = partition(arr, l, r);
        quickSort(arr, l, p[0] - 1);
        quickSort(arr, p[1] + 1, r);
    }

    private int[] partition(String[] arr, int L, int R) {
        int left = L - 1;
        int right = R;

        while (L < right) {
            if (isBigger(arr[L], arr[R])) {
                swap(arr, L, --right);
            } else if (!isBigger(arr[L], arr[R])) {
                swap(arr, L++, ++left);
            } else {
                L++;
            }
        }
        swap(arr, right, R);
        return new int[]{left + 1, right};
    }

    private boolean isBigger(String cur, String next) {
        char[] curCharArr = cur.toCharArray();
        char[] nextCharArr = next.toCharArray();
        int length = curCharArr.length > nextCharArr.length ? nextCharArr.length : curCharArr.length;

        // 按照最小的长度对比
        for (int i = 0; i < length; i++) {
            if ((int) curCharArr[i] > (int) nextCharArr[i]) {
                return true;
            } else if ((int) curCharArr[i] < (int) nextCharArr[i]) {
                return false;
            }
        }
        // 如果按照最小长度比较都是相等，则长的字符串大
        if (curCharArr.length > nextCharArr.length) {
            return true;
        }
        return false;
    }

    @Test
    public void t11() {
        int N = 275;
        int sum = 0;
        for (int i = 1; i <= N; i++) {
            sum += sumT(i);
        }
        System.out.println(sum);
    }

    private int sumT(int N) {
        if(N == 1) {
            return 2;
        }
        return sumT(N - 1) + 3;
    }



    public void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
