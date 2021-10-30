package com.github.左神算法.贪心;

import java.util.PriorityQueue;

/**
 * @author tangsong
 * @date 2021/6/14 18:52
 */
public class IPO {

    /**
     * 输入：正数数组costs、正数数组profits，正数K，正数M
     *
     * costs[i]表示i号项目的花费
     * profits[i]表示i号项目在扣除花费之后还能挣到的钱
     * K表示你只能串行的最多做K个项目
     * M表示你初始的资金
     * 说明：每做完一个项目，马上获取收益，可以支持你去做下一个项目。不能并行的做项目。
     * 输出：你最后获得的最大钱数
     *
     */

    public static class Project {
        public int cost;
        public int profit;

        public Project(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }

    /**
     * 创建2个堆，根据花费创建的小根堆，根据利益创建的大根堆
     * 将当前可以做的项目从小根堆中弹出，放入到大根堆中，然后做一个项目，然后继续从小根堆中弹出加入，这样每次都做的是利润最大的项目
     *
     */
    public int ipo(int[] costs, int[] profits, int K, int M) {
        if(costs == null || costs.length < 1 || profits == null || profits.length < 1) {
            return 0;
        }
        // 花费队列按照花费从小到大排序
        PriorityQueue<Project> cost = new PriorityQueue<Project>((a, b) -> a.cost - b.cost);
        // 利润队列按照从大到小排序
        PriorityQueue<Project> profit = new PriorityQueue<Project>((a, b) -> b.profit - a.profit);

        for (int i = 0; i < costs.length; i++) {
            cost.offer(new Project(costs[i], profits[i]));
        }
        // 一共可以做K个项目
        for (int i = 0; i < K; i++) {
            // 先将cost堆中可以做的项目移动到profit队列，会自动按照利润从大到小排序
            while (!cost.isEmpty() && M >= cost.peek().cost) {
                profit.offer(cost.poll());
            }
            // 判断当前profit队列是否有数据，如果没有数据，则表示当前无项目可做，直接结束
            if(profit.isEmpty()) {
                return M;
            }
            // 从profit队列中取出一个项目做，该项目是当前可以做的利润最多的项目
            M += profit.poll().profit;
        }

        return M;
    }



}
