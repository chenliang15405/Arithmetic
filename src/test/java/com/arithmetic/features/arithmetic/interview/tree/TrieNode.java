package com.arithmetic.features.arithmetic.interview.tree;

/**
 * Trie: 字典树、前缀树
 *
 *  从根节点开始，给定一个字符串，将字符串从根节点开始走，将字符放在链接节点的路径上，
 *  依次向下创建节点，节点中保存的是路径上字符的到达次数，并且记录该字符串的结尾次数
 *
 *  解决的问题：
 *      - 可以用来判断有多少个字符串以某个字符（某个字符串）为前缀（遍历这个字符串并判断当前字符在Trie树是否有节点，就是节点不为空）
 *      - 有多少个字符串以某个字符（某个字符串）结尾（遍历这个字符串，判断Trie中是否存在这些字符，并且不是包含这个字符串，而是以这个字符串结尾，遍历到
 *        结尾字符，并判断当前的Trie中的end是否不等于0，如果是则表示是以这个字符串结尾）
 *
 *
 * @author alan.chen
 * @date 2020/6/25 11:32 PM
 */
public class TrieNode {

    /**
     * 数组形式表示的前缀树，一般适用于数据规模不大，这里只使用在小写字母
     *   前缀树的字符并不是在节点上，而是在路径上，但是为了结构比较好表示，采用将字符放在下节点上，根节点什么都不表示
     */
    public static class Node {
        public int pass; // 经过当前节点的次数
        public int end; // 以当前节点为结尾的字符的次数
        public Node[] nexts; // 当前节点的所有子节点

        public Node() {
            nexts = new Node[26];
        }
    }

    public static class TrieTree {
        public Node root;

        public TrieTree() {
            // 根节点为空
            root = new Node();
        }


        /**
         * 前缀树中加入字符串
         *
         */
        public void insert(String word) {
            if(word == null) {
                return;
            }
            char[] chars = word.toCharArray();
            Node node = root;
            node.pass++;
            int index = 0;

            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if(node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                // 移动到下一个节点
                node = node.nexts[index];
                node.pass++;
            }
            // 达到字符串结尾，则end++;
            node.end++;
        }

        /**
         * 删除字符串
         *
         *  将每个字符经过的节点的pass减1，如果pass等于0了，表示该节点除了当前字符串没有其他字符串经过，所以
         *  可以直接将这个节点之后的所有节点删除，如果都不为0，那么就将最后一个字符的end减1
         */
        public void delete(String word) {
            if(word == null) {
                return;
            }
            char[] chars = word.toCharArray();
            Node node = root;
            // 先将根节点的pass--
            node.pass--;
            int index = 0;

            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if(--node.nexts[index].pass == 0) {
                    // 如果当前字符对应的节点的pass--之后等于0，表示没有节点经过了，直接设置为null
                    node.nexts[index] = null;
                    return;
                }
                node = node.nexts[index];
            }
            // 如果一直遍历到了最后的节点，那么将end--
            node.end--;
        }

        /**
         * 查询字符串在前缀树中出现几次
         *
         */
        public int search(String word) {
            if(word == null) {
                return 0;
            }

            char[] chars = word.toCharArray();
            Node node = root;
            int index = 0;

            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if(node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }

            return node.end;
        }

        /**
         * 查询已经加入的字符串中，有几个是以pre字符串作为前缀的
         *
         */
        public int prefixNumber(String pre) {
            if(pre == null) {
                return 0;
            }
            char[] chars = pre.toCharArray();
            Node node = root;
            int index = 0;

            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if(node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }

            return node.pass;
        }

    }


}
