package com.arithmetic.features.pattern.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author alan.chen
 * @date 2020/7/31 3:57 PM
 */
public class ProxyDemo {

    interface Itar {

        public int save(int i);

    }

    class Target implements Itar {

        @Override
        public int save(int i) {
            System.out.println("保存数据: " + i);
            return 1;
        }
    }


    /**
     * 通过Proxy创建代理类
     */
    @Test
    public void test1() {
        Itar instance = new Target();

        Itar proxyInstance = (Itar) Proxy.newProxyInstance(instance.getClass().getClassLoader(),
                instance.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        before();
                        Object invoke = method.invoke(instance, args);
                        after();

                        return invoke;
                    }
                });

        int save = proxyInstance.save(1);

        System.out.println(save);
    }



    private void before() {
        System.out.println("开启事务");
    }

    private void after() {
        System.out.println("提交事务");
    }

}
