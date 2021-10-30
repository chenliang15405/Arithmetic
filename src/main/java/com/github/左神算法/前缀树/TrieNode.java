package com.github.左神算法.前缀树;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangsong
 * @date 2021/8/29 21:19
 */
public class TrieNode {

    /**
     * 前缀树（字典树）
     *
     *  使用数组实现的前缀树  {@link com.arithmetic.features.arithmetic.interview.tree.TrieNode}
     *  适用于26个小写字母（或者可以扩展为大小写都支持）
     *
     *  1. 当前为HashMap实现的前缀树，支持大规模的字符的前缀树
     *  2. 也可以使用数组实现
     *
     */
    @Test
    public void test() {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            TrieTree trie1 = new TrieTree();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans3 = right.search(arr[j]);
                    if (ans1 != ans3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixNumber(arr[j]);
                    int ans3 = right.prefixNumber(arr[j]);
                    if (ans1 != ans3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("finish!");
    }



    public static class Node {
        public int pass;  // 经过当前节点的次数
        public int end; // 以当前节点为结尾的次数
        public Map<Integer, Node> nexts; // key: 节点值，value：下一级节点

        public Node() {
            nexts = new HashMap<>();
        }
    }

    public static class TrieTree {
        public Node root;

        public TrieTree() {
            root = new Node();
        }

        // 插入一个字符串
        public void insert(String word) {
            if(word == null) {
                return;
            }
            char[] chars = word.toCharArray();
            Node node = root;
            int index = 0;
            node.pass++;

            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if(node.nexts.get(index) == null) {
                    node.nexts.put(index, new Node());
                }
                node = node.nexts.get(index);
                node.pass++;
            }
            node.end++;
        }

        // 删除字符串
        public void delete(String word) {
            if(word == null) {
                return;
            }
            // 先查询字符串是否包含在前缀树中
            if(search(word) == 0) {
                return;
            }
            char[] chars = word.toCharArray();
            Node node = root;
            int index = 0;
            node.pass--;

            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if(node.nexts.get(index).pass - 1 == 0) {
                    // 如果减1之后==0，则证明该节点之后的所有节点都只有当前字符串经过，那么直接删除后续的所有节点链路，让GC回收即可
                    node.nexts.remove(index);
                    return;
                }
                // 否则将当前的pass-1
                node = node.nexts.get(index);
                node.pass--;
            }
            node.end--;
        }

        // 搜索单词加入过多少次
        public int search(String word) {
            if(word == null) {
                return 0;
            }
            char[] chars = word.toCharArray();
            Node node = root;
            int index = 0;

            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                // 判断每个字符是否存在链路，并沿着节点向下寻找
                if(!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            // 如果可以找到，那么返回end
            return node.end;
        }


        // 在加入的所有字符串中，有多少个字符串以pre字符串为前缀
        public int prefixNumber(String pre) {
            if(pre == null) {
                return 0;
            }
            char[] chars = pre.toCharArray();
            Node node = root;
            int index = 0;

            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                // 判断是否存在该字符
                if(!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            // 返回pass就表示有多少字符串以pre为前缀
            return node.pass;
        }
    }


    // 正确的方法  for test
    public static class Right {

        private HashMap<String, Integer> box;

        public Right() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }


    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }


}
