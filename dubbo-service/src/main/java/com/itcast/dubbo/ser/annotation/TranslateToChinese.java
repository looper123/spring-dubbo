package com.itcast.dubbo.ser.annotation;

import java.lang.annotation.*;

/**
 * Created by zebon lu on 2017/6/27.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TranslateToChinese {


    String relationShip() default "";


    String getMethodName() default "";
}
