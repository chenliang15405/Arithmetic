package com.github.bstation.string;

import org.junit.Test;

import java.util.*;

/**
 * @author tangsong
 * @date 2021/6/1 21:48
 */
public class WordMinPath {

    /**
     * 给定两个字符串，记为start和to，在给定一个字符串列表list，list中一定包含to list中没有重复的字符串，所有的字符串都是小写
     * 规定：start每次只能改变一个字符，最终的目标是彻底变成to，但是每次变成的新的字符串必须在list中存在
     * 请返回所有最短的变换路径
     * 【举例】
     * start="abc" to="cab" list={"cab", "acc", "cbc", "ccc", "cac", "cbb", "aab", "abb"}
     * 转换路径的方法有很多种，但是所有最短的转换路径如下：
     * abc -> abb -> aab -> cab
     * abc -> abb -> cbb -> cab
     * abc -> cbc -> cac -> cab
     * abc -> cbc -> cbb -> cab
     *
     */

    @Test
    public void test() {
        String start = "abc";
        String to = "cab";
        List<String> list = new ArrayList<String>(){{
            add("cab");
            add("acc");
            add("cbc");
            add("ccc");
            add("cac");
            add("cbb");
            add("aab");
            add("abb");
        }};

        List<List<String>> minPath = getMinPath(start, to, list);

        System.out.println(minPath.toString());
    }


    /**
     * 可以通过遍历的方式将字符串的每个字符都修改一次组成一个新的字符串列表，组成一个字符串和它的所有邻接字符串（只修改一次就可以到达的字符串）
     * 然后使用bfs计算每个字符串到start字符串的最小距离
     * 从start字符串开始使用dfs计算每个最短距离的邻接字符串，每个邻接字符串的距离必须和当前字符串的距离保持为1，因为每次只能修改一次（距离其实就是表示
     * 修改多少次可以到达），dfs可以计算到所有的最短路径
     *
     *
     * start 是开始字符串
     * end  目标字符串
     * list 可以选择的字符串列表
     *
     */
    public List<List<String>> getMinPath(String start, String end, List<String> list) {
        // 将start字符串加入到列表中，下面就就可以直接计算出start字符串的所有邻接字符串，以及start字符串距离邻接字符串的距离
        list.add(start);
        // 计算每个字符串的邻接字符串
        Map<String, List<String>> nexts = getNexts(list);
        // 计算每个字符串到start字符串的最小距离，返回值的含义就是每个字符串到start字符串的最下距离
        Map<String, Integer> distances = getDistance(start, nexts);

        // 初始化pathList保存路径， 使用LinkedList，因为下面的递归需要弹出最后一个元素
        LinkedList<String> pathList = new LinkedList<>();
        // 保存所有的最小路径
        List<List<String>> res = new ArrayList<>();
        // 根据邻接字符串，距离start字符串的最近距离，可以直接计算start字符串每次修改一个字符串之后的所有字符串，然后再修改一次之后的所有字符串，直到到达目标字符串
        getShortestPath(start, end, nexts, distances, pathList, res);

        return res;
    }

    /**
     * cur: 出发的字符串
     * to： 目的地
     * nexts: 邻居表
     * distances: 最短距离表（距离start字符串的最短距离）
     * path： 收集每次沿途走过的路径
     * res: 最终的结果集，将每次能够到的最短距离保存到res中
     *
     *  因为是start字符串每次修改一次，兵器现在已经有了距离字符串列表和邻接字符串列表，那么每次修改一个字符串，有很多条路可以选择，但是每次都要判断一条路
     *  是否可以走到最终的目标字符串，所以使用DFS算法，可以优先判断每一条路径是否可以到达最终的目标字符串，如果到达则收集，没有到达则重新回溯重试
     *
     *  深度优先遍历
     */
    private void getShortestPath(String cur,
                                 String to,
                                 Map<String, List<String>> nexts,
                                 Map<String, Integer> distances,
                                 LinkedList<String> pathList,
                                 List<List<String>> res) {
        // 将当前的字符串加入到路径中
        pathList.add(cur);
        if(to.equals(cur)) {
            // 如果已经到达目标字符串，则将当前的路径加入到结果集中
            res.add(new LinkedList<>(pathList));
        } else {
            // 拿到当前所有的邻接字符串，并判断距离，即只需要修改一次就可以到达的字符串去进行尝试
            for (String next : nexts.get(cur)) {
                // 判断距离是否为当前距离+1 即判断当前字符串是否修改1个字符就可以到达邻接字符
                // （邻接字符有可能出现原始字符或者距离原始字符为1的距离，这样的话，这个字符串就不需要走这条路径，因为距离为0就是原始字符本身，那就不需要走回去，因为求的是最短的距离
                //  如果距离为1，那么原始字符修改一次就可以到达的字符串，也不需要再走回去，这样的话就修改了2次，所以每次的距离必须是当前的字符串的距离+1，这样才保证了每次都是向前走的，不是向回走的字符串，才可以计算到最短路径）
                if(distances.get(cur) + 1 == distances.get(next)) {
                    getShortestPath(next, to, nexts, distances, pathList, res);
                }
            }
        }
        // 如果最终都没有到达目标字符串，那么就是当前这条路径无法到达，需要将当前的字符串从路径中移除， LinkedList是队列，可以直接操作队列尾部
        // 深度优先遍历擦除轨迹，当回到递归的上一个元素时，防止LinkedList还有数据
        pathList.pollLast();
    }

    /**
     * 计算每个字符串和start字符串的最小距离，每次修改一个字符就是一个距离
     *
     * 使用bfs算法，因为start字符串也在nexts列表中，所以可以直接计算start字符串的所有邻接字符串到start的距离，使用bfs算法，
     * 然后计算每个邻接字符串的邻接字符串到start的距离
     *
     */
    private Map<String, Integer> getDistance(String start, Map<String, List<String>> nexts) {
        Map<String, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        // bfs遍历，使用队列
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        // 使用Set结构，避免重复字符串，因为可能会有修改之后相同的字符串，可以直接过滤掉，因为不需要修改两次相同的，会有修改回去的情况 A -> B  B->A
        Set<String> set = new HashSet<>();
        set.add(start);
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            // 拿到当前字符串的所有邻接子字符串
            List<String> list = nexts.get(cur);
            for (String str : list) {
                if(!set.contains(str)) {
                    set.add(str);
                    // 因为是邻接字符串，所以距离就是当前字符串距离+1
                    distances.put(str, distances.get(cur) + 1);
                    queue.offer(str);
                }
            }
        }
        return distances;
    }

    /**
     * 获取列表中的每个字符串的邻接字符串，就是将列表中的字符串组织起来
     *  组成每个字符串修改一个字符之后，可以变成哪些字符串，这些字符串必须是存在提供的列表中
     *
     */
    private Map<String, List<String>> getNexts(List<String> words) {
        Set<String> dict = new HashSet<>(words); // 将所有字符串放入set
        Map<String, List<String>> nexts = new HashMap<>();
        for (int i = 0; i < words.size(); i++) {
            // 将每个字符串以及它的所有邻接字符串保存到map中，一一对应
            nexts.put(words.get(i), getNext(words.get(i), dict));
        }
        return nexts;
    }

    /**
     * 计算每个字符串的所有邻接字符串
     */
    private List<String> getNext(String word, Set<String> dict) {
        List<String> res = new ArrayList<>();
        char[] chs = word.toCharArray();
        for (char cur = 'a'; cur <= 'z'; cur++) {
            for (int i = 0; i < chs.length; i++) {
                // 只有当不等于的时候才保存到邻接字符串中，邻接字符串不包含自身
                if(chs[i] != cur) {
                    char temp = chs[i]; // 先将自身原始的值保存到temp变量，为了后续重新还原为原本的字符串，否则会影响后面的字符串
                    chs[i] = cur;
                    // 只有当给到的字符串列表中包含修改一个字符的字符串，才记录，否则是无效数据，因为每次修改的字符串必须在给到的列表中
                    if(dict.contains(String.valueOf(chs))) {
                        res.add(String.valueOf(chs));
                    }
                    // 还原为没有修改之前的字符串
                    chs[i] = temp;
                }

            }
        }
        return res;
    }


}
