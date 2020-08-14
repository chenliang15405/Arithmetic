package com.arithmetic.features.lb;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 负载均衡算法：轮询
 *
 * @author alan.chen
 * @date 2020/7/25 7:27 PM
 */
public class RobinLoadBalance implements LoadBalance {


    private AtomicInteger atomicInteger = new AtomicInteger(1);


    /**
     * 获取当前的请求次数，防止高并发情况，使用CAS修改
     * @return
     */
    public final int getIndex() {
        int current;
        int next;
        do {
            // 获取到当前的请求次数，并对请求次数+1，通过CAS修改请求次数，修改失败则自旋，成功则返回
            current = atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0 : current + 1;
        } while(!atomicInteger.compareAndSet(current, next));

        System.out.println("*******next " + next);
        return next;
    }


    /**
     * 从所有的服务器List中获取到指定的节点
     * @param list
     */
    @Override
    public void choose(List<String> list) {
        int index = getIndex();
        // 根据请求的次数对所有的服务器节点取余
        int i = index % list.size();

        System.out.println("当前节点： " + list.get(i));
    }

}
