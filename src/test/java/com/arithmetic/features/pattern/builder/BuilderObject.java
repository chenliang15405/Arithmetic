package com.arithmetic.features.pattern.builder;

/**
 * 建造者模式
 *
 * @author alan.chen
 * @date 2020/7/22 11:06 PM
 */
public class BuilderObject {

    private String name;
    private int age;
    private int num;

    private BuilderObject(BuilderInner builderInner) {
        this.name = builderInner.name;
        this.age = builderInner.age;
        this.num = builderInner.num;
    }

    public static class BuilderInner {
        private String name;
        private int age;
        private int num;

        public BuilderInner setName(String name) {
            this.name = name;
            return this;
        }

        public BuilderInner setAge(int age) {
            this.age = age;
            return this;
        }

        public BuilderInner setNum(int num) {
            this.num = num;
            return this;
        }

        public BuilderObject builder() {
            return new BuilderObject(this);
        }
    }

    @Override
    public String toString() {
        return "BuilderObject{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", num=" + num +
                '}';
    }


    public static void main(String[] args) {
        BuilderObject obj = new BuilderInner()
                                            .setName("1")
                                            .setAge(2)
                                            .setNum(3)
                                            .builder();
        System.out.println(obj);
    }



}
