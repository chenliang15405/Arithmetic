package com.arithmetic.features.proxy.静态代理;

/**
 * @author alan.chen
 * @date 2020/8/1 12:20 PM
 */
public class StaticProxy implements P {

    private P target;
    public StaticProxy(P target) {
        this.target = target;
    }

    /**
     * 静态代理，手动实现代理类，并且如果需要代理类过多，需要维护大量的代理类
     * @return
     */
    @Override
    public int add() {
        System.out.println("开启事务");
        int add = target.add();
        System.out.println("关闭事务");
        return add;
    }

    @Override
    public void print() {
        target.print();
    }
}
