package com.arithmetic.features.json;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author alan.chen
 * @date 2020/8/4 10:34 AM
 */
public class JsonParse {


    /**
     * 手动实现json解析，通过反射保存对象数据
     *
     *
     */
    @Test
    public void test() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String json = "{\"name\":\"张三\",\"age\":18,\"gender\":\"true\"}";
        String s = json.replaceAll("\\{|\\}", "");
        System.out.println(s);
        s = s.replaceAll("\"", "");

        Person person = new Person();

        String[] split = s.split(",");
        for (String item : split) {
            String[] keyValue = item.split(":");
            if(keyValue.length >= 2) {
                Field field = person.getClass().getDeclaredField(keyValue[0]);
                field.setAccessible(true);
                //field.set(person, keyValue[1]); // 也可以通过filed来直接调用set方法
                Method method = person.getClass().getDeclaredMethod("set" + keyValue[0].substring(0,1 ).toUpperCase() + keyValue[0].substring(1).toLowerCase() , field.getType());
                method.setAccessible(true);
                System.out.println("method name " + method.getName() + " value: " + keyValue[1] + " type: " + keyValue[1].getClass().getTypeName());

                // 通过类型判断
                Type type = field.getGenericType();
                if(type instanceof Class<?>){
                    Class<?> cls = (Class<?>)type;
                    if(String.class.isAssignableFrom(cls)){
                        method.invoke(person, keyValue[1]);
                        System.out.println("我是String");
                    } else if(int.class.isAssignableFrom(cls)) {
                        method.invoke(person, Integer.valueOf(keyValue[1]));
                    } else if(boolean.class.isAssignableFrom(cls)) {
                        method.invoke(person, Boolean.valueOf(keyValue[1]));
                    }
                }

                // 通过类型比较
                //if(field.getType().getTypeName().equals("int")) {
                //    method.invoke(person, Integer.valueOf(keyValue[1]));
                //} else if(field.getType().getTypeName().equals("boolean")) {
                //    method.invoke(person, Boolean.valueOf(keyValue[1]));
                //} else {
                //    method.invoke(person, keyValue[1]);
                //}
            }
        }

        System.out.println(person);
    }



    class Person {

        private String name;
        private int age;
        private boolean gender;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean isGender() {
            return gender;
        }

        public void setGender(boolean gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    '}';
        }
    }

}
