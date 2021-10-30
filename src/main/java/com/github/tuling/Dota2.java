package com.github.tuling;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author tangsong
 * @date 2021/5/7 0:28
 */
public class Dota2 {

    /**
     * Dota2 参议院  （贪心算法）
     *
     * 给定一个字符串，字符串中的R代表一个阵营，D代表另一个阵营，每个字母都可以行驶2个权利，一个是禁用其他阵营的用户，一个是当所有
     * 有权利投票的用户都是同一阵营的时候，宣布胜利。
     * 当一个用户行使权利之后，如果没有被禁用，那么在下一轮中还可以使用权限
     *
     */

    @Test
    public void test() {
        String senate = "RDDRD";

        System.out.println(predictPartyVictory(senate));
    }

    private String predictPartyVictory(String senate) {
        // 为什么使用队列 -> 因为存在多轮，并且保证每一轮的相对顺序，所以使用队列，保证先后顺序
        Queue<Integer> rQueue = new LinkedList<>();
        Queue<Integer> dQueue = new LinkedList<>();

        // 将每种字符的下标保存到队列中
        for (int i = 0; i < senate.length(); i++) {
            if(senate.charAt(i) == 'R') {
                rQueue.offer(i);
            } else {
                dQueue.offer(i);
            }
        }

        // 从队列中取出来每个位置的下标，进行判断，下标小的可以进入到下一轮，因为这个用户可以先将另一个阵营中小的下标的用户禁用，这样最大化赢的几率
        while (!rQueue.isEmpty() && !dQueue.isEmpty()) {
            Integer r = rQueue.poll();
            Integer d = dQueue.poll();
            if(r < d) {
                rQueue.offer(r + senate.length());
            } else {
                dQueue.offer(d + senate.length());
            }
        }
        // 如果某个队列不为空，则该队列的阵营就是赢
        return rQueue.isEmpty() ? "D" : "R";
    }


}
