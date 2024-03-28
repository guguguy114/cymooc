package com.cykj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 * 数据库的表名
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/25 22:14
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    String value();
}
