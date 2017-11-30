package com.itcast.dubbo.pro.provide;

import com.alibaba.dubbo.container.Main;
import com.alibaba.dubbo.container.jetty.JettyContainer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by zebon lu on 2017/5/15.
 */
public class Provider {

    public static void main(String args[]) throws IOException {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring.xml");
//        context.start();
//        System.in.read();
//        一共有四种container 可以启动  默认是spring container
//        args = new String[1];
//        args[0]="jetty";
//        Main.main(args);
        Main.main(args);
    }
}
