package com.cykj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 * servlet注解，用于创建servlet集合
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/17 14:00
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Servlet {
    String value();
}
