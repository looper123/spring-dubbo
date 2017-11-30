package com.itcast.dubbo.ser.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by zebon lu on 2017/5/27.
 * 标记实体中的字段中文名称
 */
@Retention(RetentionPolicy.RUNTIME)//保留到运行时
@Target({FIELD, TYPE})//注解的作用域
@Documented //被java doc工具记录
public @interface SheetHeader {

    String comments() default "";

    int index() default 0;
}
