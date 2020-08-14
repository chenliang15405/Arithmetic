package com.arithmetic.features.proxy;

import java.lang.annotation.*;

/**
 * @author alan.chen
 * @date 2020/8/1 11:49 AM
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyTransaction {

    String value() default "";

    int timeout() default -1;

}
