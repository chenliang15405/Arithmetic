package com.arithmetic.features.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author alan.chen
 * @date 2020/7/13 6:19 PM
 */
public class TransactionProxy implements InvocationHandler {

    private P p;
    public TransactionProxy(P p) {
        this.p = p;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(p, args);
        after();

        /**
         * 如果方法有返回值，则必须返回该对象
         */
        return invoke;
    }

    private void before() {
        System.out.println("前置方法");
    }
    private void after() {
        System.out.println("后置方法");
    }

}
