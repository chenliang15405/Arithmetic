package com.github.bstation;

import java.nio.file.Path;
import java.util.TreeMap;

/**
 * @author tangsong
 * @date 2021/5/27 22:46
 */
public class GetFolderTree {

    /**
     * 前缀树问题：trie（字典树）
     *
     * 给定一个字符串类型的数组arr，譬如：
     * String[] arr = {"b\st", "d\", "a\d\e", "a\b\c"}
     * 把这些路径中蕴含的目录结构打印出来，子目录直接列到父目录之下，并比父目录向右进两格，就像这样
     * a
     *   b
     *     c
     *   d
     *     e
     * b
     *   st
     * d
     *
     * 同一级的需要按字母顺序排列不能乱
     *
     */

    public static class Node {
        // 上一个节点是通过哪条路到我的 (前缀树的节点一般是不放数据，在路径上放数据，但是这里为了方便打印，也在节点上放一份，
        // 这里的path就表示路径， Node就表示节点，所以节点中包含路径，路径的值就是节点的值)
        public String path;
        // key: node下级的路  value: node在key这条路上对应的节点是什么
        public TreeMap<String, Node> nextMap;

        public Node(String path) {
            this.path = path;
            this.nextMap = new TreeMap<>();
        }
    }


    /**
     * 构建前缀树，并深度优先遍历打印前缀树即可
     *   前缀树的创建需要按照字典顺序，所以需要使用到treeMap，那么就会自动根据key排序，这样建立好树之后，每个节点的子树节点的顺序都已经排好序了
     *   直接使用深度优先遍历打印即可
     *
     */
    public void printFolder(String[] folderPaths) {
        if(folderPaths == null || folderPaths.length == 0) {
            return;
        }
        // 生成字典树
        Node head = generateFolderTree(folderPaths);

        // 递归打印字典树，深度优先遍历
        printProcess(head, 0);
    }

    /**
     * 深度优先遍历，按照层进行遍历
     *
     *  level主要是为了保证打印时每一层的空格，在深度递归的时候，抱枕最底层的空格和回溯的时候父节点的空格
     *
     */
    private void printProcess(Node head, int level) {
        if(level != 0) {
            // 当level=0表示空节点不打印
            System.out.println(get4nSpace(level) + head.path);
        }
        // 先打印当前节点，然后向下遍历
        for (Node node : head.nextMap.values()) {
            // map的值已经根据key排序好了，并且key和value都是相同的，一个是路径，一个是节点，这里直接打印节点的path属性
            printProcess(node, level + 1);
        }
    }

    private String get4nSpace(int level) {
        String res = "";
        for (int i = 1; i < level; i++) {
            res += "    ";
        }
        return res;
    }

    /**
     * 生成字典树
     */
    private Node generateFolderTree(String[] folderPaths) {
        // 头结点默认是空字符串，用来做标识
        Node head =  new Node("");

        for (String folderPath : folderPaths) {      // "a\b\c"
            // 分割当前字符串
            String[] split = folderPath.split("\\\\");  // 这里既需要转义java的\ 还需要转义正则中的\, 实际就是 \
            Node cur = head;
            // 遍历字符串，构建一个完整路径
            for (int i = 0; i < split.length; i++) {
                // 判断同一级否有相同的节点值
                if (!cur.nextMap.containsKey(split[i])) {
                    // 作为当前节点的下一级的节点和设置路径, 这里的treeMap自动保证put的路径的值顺序（同一级按照顺序）
                    cur.nextMap.put(split[i], new Node(split[i]));
                }
                // 向下一个节点移动，因为每个字符都是当前节点的下一个节点
                cur = cur.nextMap.get(split[i]);
            }
        }
        return head;
    }

}
