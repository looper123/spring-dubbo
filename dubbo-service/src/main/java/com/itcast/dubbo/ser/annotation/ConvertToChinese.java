package com.itcast.dubbo.ser.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by zebon lu on 2017/6/26.
 */
@Retention(RetentionPolicy.RUNTIME)//保留到运行时
@Target({FIELD, TYPE,METHOD})//注解的作用域
@Documented //被java doc工具记录
public @interface ConvertToChinese {

     String  jsonString() default "" ;

     String  FieldName() default  "";

}
