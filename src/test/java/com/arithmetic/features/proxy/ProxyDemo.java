package com.arithmetic.features.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author alan.chen
 * @date 2020/7/13 6:00 PM
 */
public class ProxyDemo {


    @Test
    public void test() {
        P target = new Child();

        TransactionProxy transactionProxy = new TransactionProxy(target);

        P proxyInstance = (P) Proxy.newProxyInstance(Child.class.getClassLoader(),
                Child.class.getInterfaces(), transactionProxy);

        System.out.println(proxyInstance.getClass().getName());

        proxyInstance.add();
    }


    /**
     * 动态代理实现事务
     */
    @Test
    public void test2() {
        P target = new Child();

        P jdkProxy = (P) getJdkProxy(target);

        jdkProxy.add(); // 有注解，开启事务
        jdkProxy.print(); // 无注解，关闭事务
    }


    /**
     * 根据目标对象获取JDK动态代理对象
     *
     * @param target 目标对象
     * @return
     */
    public static Object getJdkProxy(Object target) {
        return Proxy.newProxyInstance(Child.class.getClassLoader(), Child.class.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        /**
                         * 这个method 参数是接口中的方法，如果注解在实现类中则无法判断是否存在注解，直接当注解在接口上，才可以
                         * 通过method判断是否有该注解，如果注解在实现类，需要通过target对象获取实现类的mthod
                         */
                        // 获取指定实现类的method
                        Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());

                        if (method.isAnnotationPresent(MyTransaction.class) || method.getDeclaringClass().isAnnotationPresent(MyTransaction.class)
                                || targetMethod.getDeclaringClass().isAnnotationPresent(MyTransaction.class)
                                || targetMethod.isAnnotationPresent(MyTransaction.class)) {

                            System.out.println("开启事务");
                            System.out.println("注解的value : " + targetMethod.getDeclaredAnnotation(MyTransaction.class).value());
                            System.out.println("注解的timeout: " + targetMethod.getDeclaredAnnotation(MyTransaction.class).timeout());

                            Object invoke = method.invoke(target, args);

                            System.out.println("关闭事务");

                            return invoke;
                        }
                        Object invoke = method.invoke(target, args);
                        return invoke;
                    }
                });
    }

}
