package com.itcast.dubbo.web.consum;

import com.itcast.dubbo.ser.service.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zebon lu on 20 17/5/15.
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
//        不使用注解（适合代码跟踪 ，不适合实际运用）
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring.xml");
        context.start();

        DemoService demoService = (DemoService) context.getBean("demoService");
        String hello = demoService.sayHello("world");

        System.out.println(hello);



    }
}
