package com.arithmetic.features.issue.singleton;

/**
 * 枚举实现单例模式   线程安全
 *
 * 并且枚举实现的单例模式，不会被破坏，因为反射时会校验，如果是枚举类型，则抛出异常
 *                                如果是序列化，判断如果是枚举，则通过枚举的方式获取单例对象
 *
 * 枚举的底层是enum类，成员都是static修饰，也是通过类加载保证线程安全
 *
 * @author alan.chen
 * @date 2020/5/28 5:42 PM
 */
public class  Singleton5 {

    private Singleton5() {}


    // 定义枚举，
    public enum Singleton {
        INSTANCE;

        private final Singleton5 singleton5;

        private Singleton() {
            singleton5 = new Singleton5();
        }

        private Singleton5 getInstance(){
            return singleton5;
        }

    }

    public static Singleton5 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }


    public static void main(String[] args) {
        System.out.println(Singleton5.getInstance());
    }


    // 完整的枚举单例实现
    //private Singleton5() {}
    //
    //static enum Single {
    //    INSTANCE;
    //
    //    private Singleton5 singleton5;
    //
    //    private Single() {
    //        singleton5 = new Singleton5();
    //    }
    //
    //    public Singleton5 getInstance() {
    //        return singleton5;
    //    }
    //}
    //
    //public static void getInstannce() {
    //    return Single.INSTANCE.getInstance();
    //}

}
