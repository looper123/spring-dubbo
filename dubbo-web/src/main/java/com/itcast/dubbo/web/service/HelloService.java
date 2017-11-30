package com.itcast.dubbo.web.service;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.dubbo.ser.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * Created by zebon lu on 2017/5/20.
 * spring的bean 用来调用服务
 */
@Service(value = "helloService")
public class HelloService {

    @Reference
    private DemoService demoService;

    public String invokeSayHello(String name){
        name+="dubbo-web-service\n";
       return  demoService.sayHello(name);
    }

}
