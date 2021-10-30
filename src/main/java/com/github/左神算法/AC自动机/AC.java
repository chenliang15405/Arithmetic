package com.github.左神算法.AC自动机;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author tangsong
 * @date 2021/8/14 21:52
 */
public class AC {

    /**
     * AC自动机，解决问题：一段文本或者字符串 在多个关键词列表中可以匹配到多少个关键词
     *
     * 例如： 给定一个大文章：abcdefdksloeikkzbd
     *       有多个敏感词：abc   abcde   cde  slo
     *       大文章中可以匹配到多少个敏感的关键词
     *
     *
     * 前缀树 + fail指针
     *
     */
    @Test
    public void test() {
        ACAutomation ac = new ACAutomation();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("abcdheks");
        // 设置fail指针
        ac.build();

        List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
        for (String word : contains) {
            System.out.println(word);
        }

    }



    // 前缀树的节点
    public static class Node {
        // 如果一个node，end为空，则表示不是结尾
        // 如果一个node的不为空，则表示这个点是某个字符串的即为，end的值就是这个字符串
        public String end;
        // 只有在上面的end变量不为空的时候，endUser才有意义
        // 表示，这个字符串之前没有加入过答案列表中
        public boolean endUse;
        public Node fail; // 每个节点都有一个fail指针，fail可以表示为匹配失败后，指向的可以匹配的最长前缀的位置
        public Node nexts[]; // 当前节点的下面的所有的节点，因为每个节点都可以记录下面的每个不同的字符

        public Node() {
            endUse = false;
            end = null;
            fail = null;
            nexts = new Node[26]; // 因为是26个字母，所以这里初始化为26个长度

        }
    }

    // AC自动机对象
    public static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        // 这里只是构建前缀树，还没有构建fail指针
        public void insert(String s) {
            char[] str = s.toCharArray();
            Node cur = root;
            int index =0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                // 如果当前的字符已经记录到前缀树了，那么只需要向下查看是否还需要额外记录，已经记录过的节点无需重复记录
                // 因为前缀树共享同一个前缀
                if(cur.nexts[index] == null) {
                    cur.nexts[index] = new Node();
                }
                // 向下移动
                cur = cur.nexts[index];
            }
            // 记录当前的字符串在某个节点上
            cur.end = s;
        }


        // 构建前缀树中的每个节点的fail指针
        public void build() {
            // bfs构建
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node cfail = null;
            while (!queue.isEmpty()) {
                // 当前节点弹出
                // 当前节点的所有后代节点加入到队列中
                // 当前节点给它的子节点设置fail指针
                // cur -> 父节点
                cur = queue.poll();
                // 遍历每个节点下的所有路
                for (int i = 0; i < 26; i++) {
                    // 找到有效的路
                    if(cur.nexts[i] != null) {
                        // 先将每个节点的fail指针指向root，因为最后找不到对应的fail指针时，会将fail指向root，那么先将fail指向root
                        // 然后再去找到对应的fail
                        cur.nexts[i].fail = root;
                        // 找到当前节点的fail指针（即在设置子节点的fail指针时，找到父节点的fail指针是否可以找到相同的子节点，cur就是父节点）
                        cfail = cur.fail;
                        while (cfail != null) {
                            // 如果cur指向的fail指针节点的子节点包含cur的子节点，那么将cur的子节点的fail指向该节点
                            if(cfail.nexts[i] != null) {
                                cur.nexts[i].fail = cfail.nexts[i];
                                break;
                            }
                            cfail = cfail.fail;
                        }
                        // 将子节点加入到队列
                        queue.offer(cur.nexts[i]);
                    }
                }
            }
        }


        // content: 大文章，找到大文章包含的敏感词列表
        public List<String> containWords(String content) {
            char[] str = content.toCharArray();
            Node cur = root;
            Node follow = null;
            int index = 0;
            List<String> ans = new ArrayList<>();

            for (int i = 0; i < str.length; i++) {
                // 当前的路
                index = str[i] - 'a';
                // 如果在这条路上没有配出来，就随着fail走向下一条路径
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                // 如果跳出while循环，则有两种可能
                // 1. 现在来到的路径是可以继续匹配的
                // 2. 现在来到的节点，就是前缀树的根节点
                cur = cur.nexts[index] != null ? cur.nexts[index] : root; // 所以判断当前是否可以继续匹配还是根节点
                // 使用follow节点在每个节点时，判断当前是否是敏感词节点，如果是，则记录当前敏感词并随着fail节点查询一遍，并记录所有
                // 可以匹配到的敏感词
                follow = cur;
                while (follow != root) {
                    // 判断是否已经记录过了
                    if(follow.endUse) {
                        break;
                    }
                    // 不同的需求， 在这一段之间修改
                    if(follow.end != null) {
                        // 如果当前节点是敏感词节点，则记录当前的答案
                        ans.add(follow.end);
                        follow.endUse = true;
                    }
                    // 不同的需求， 在这一段之间修改
                    follow = follow.fail;
                }
            }
            return ans;
        }

    }









}
