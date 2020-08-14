package com.arithmetic.features.ioc;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现SpringIOC容器，解决循环依赖
 *
 * @author alan.chen
 * @date 2020/7/20 11:26 AM
 */
public class IocContainerDemo {

    //private Map<String, Object> map = new HashMap<>();

    private static Map<String, Object> map = new ConcurrentHashMap<>();

    @Test
    public void test() throws InstantiationException, IllegalAccessException {
        Class[] arr = new Class[]{A.class, B.class};
        for (Class in : arr) {
            Object instance = getInstance(in);
            System.out.println(instance);
        }
    }

    /**
     * 实例化bean
     *
     */
    public Object getInstance(Class in) throws IllegalAccessException, InstantiationException {
        String name = in.getSimpleName().toLowerCase();

        if (map.get(name) != null) {
            return map.get(name);
        }
        Object obj = in.newInstance();
        map.put(name, obj);

        populate(obj);

        return obj;
    }

    /**
     * 依赖注入
     * @param obj
     */
    private void populate(Object obj) throws IllegalAccessException, InstantiationException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> type = field.getType();
            Object o = map.get(type.getSimpleName().toLowerCase());
            if (o != null) {
                // 属性.set(被修改的实例对象，赋值的对象)
                field.set(obj, o);
            } else {
                // TODO 递归
                getInstance(type);
            }
        }
    }


}
