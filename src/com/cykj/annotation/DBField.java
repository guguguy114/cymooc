package com.cykj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 * 标记pojo文件对应属性的数据库名称的注解
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/25 22:19
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBField {
    String value() default "";
}
